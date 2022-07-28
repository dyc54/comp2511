package dungeonmania;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import dungeonmania.battle.Enemy;
import dungeonmania.collectableEntities.Bomb;
import dungeonmania.collectableEntities.Useable;
import dungeonmania.collectableEntities.durabilityEntities.Durability;
import dungeonmania.collectableEntities.durabilityEntities.DurabilityEntity;
import dungeonmania.collectableEntities.durabilityEntities.PotionEntity;
import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.BuildableRecipe;
import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.MidnightArmour;
import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.Sceptre;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.BonusStrategy;
import dungeonmania.strategies.PlayerMovementStrategy;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.attackStrategies.WeaponableAttackStrategy;
import dungeonmania.strategies.battleStrategies.BattleStrategyWithEnemy;
import dungeonmania.strategies.battleStrategies.BattleStrategyWithPlayer;
import dungeonmania.strategies.defenceStrategies.ArmorableStrategy;
import dungeonmania.strategies.defenceStrategies.DefenceStrategy;
import dungeonmania.util.Position;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.inventories.Inventory;

public class Player extends Entity implements PlayerMovementStrategy, PotionEffectSubject, Enemy, SceptreEffectSubject, BattleStrategyWithEnemy, BattleStrategyWithPlayer {
    // private int attack;
    private AttackStrategy attack;
    private DefenceStrategy defence;
    private double health;
    private Inventory inventory;
    private Position next;
    private boolean stay;
    private DungeonMap map;
    private final Location previousLocation;
    private Queue<PotionEntity> effects;
    private List<PotionEffectObserver> observers;
    private List<Sceptre> sceptres;
    private List<SceptreEffectObserver> SceptreObservers;
    private Durability durabilities;
    private int buildCounter;
    public Player(String type, int x, int y, double attack, double health, DungeonMap map) {
        super(type, x, y);
        this.attack = new WeaponableAttackStrategy(attack);
        this.defence = new ArmorableStrategy(0);
        this.health = health;
        previousLocation = Location.AsLocation(x, y);
        inventory = new Inventory();
        this.map = map;
        stay = false;
        buildCounter = 0;
        observers = new ArrayList<>();
        effects = new ArrayDeque<>();
        sceptres = new ArrayList<>();
        this.SceptreObservers = new ArrayList<>();
    }

    public void addsceptreObservers (SceptreEffectObserver observer) {
        this.SceptreObservers.add(observer);
    }

    public void addeffect(PotionEntity e) {
        effects.add(e);
    }

    public AttackStrategy getAttackStrategy() {
        return attack;
    }

    public DefenceStrategy getDefenceStrayegy() {
        return defence;
    }

    public double getHealth() {
        return health;
    }

    public List<Entity> getInventoryList() {
        return inventory.getAllInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setBattleUsedDuration() {
        inventory.getAllInventory().forEach( entity ->{
            if (entity instanceof DurabilityEntity) {
                ((DurabilityEntity) entity).setDurability();
            }
        });
    }

    public void cleardisusableItem() {
        List<Entity> entities = new LinkedList<>();
        inventory.getAllInventory().forEach( entity ->{
            System.out.println(entity.getType());
            if (entity instanceof DurabilityEntity && ((DurabilityEntity) entity).checkDurability()) {
                entities.add(entity);
            }
        });
        entities.stream().forEach(entity -> inventory.removeFromInventoryList(entity.getEntityId(), this));
        if (hasEffect() && getCurrentEffect().checkDurability()) {
            effects.poll();
            notifyPotionEffectObserver();
        } else if (hasEffect()) {
            getCurrentEffect().setDurability();

        }

    }
    public void removeInventoryList(Entity item) {
        inventory.removeFromInventoryList(item);
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }
    public Position getDirection() {
        return next;
    }
    public boolean getStay() {
        return stay;
    }

    public List<ItemResponse> getItemResponse() {
        List<ItemResponse> inventory = new ArrayList<>();
        getInventoryList().stream()
                .forEach(item -> inventory.add(new ItemResponse(item.getEntityId(), item.getType())));
        return inventory;
    }

    private void interactAll(Location curr, DungeonMap map, Position p) {
        Location next = curr.getLocation(p);
        System.out.println(String.format("interact at %s", next.toString()));
        // Deal with Concurrent Expection Error
        List<Interactability> buffer = new ArrayList<>();
        for (Iterator<Entity> iterator = map.getEntities(next).iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            if (entity instanceof Interactability && entity.getLocation().equals(next)) {
                Interactability iteratableEntity = (Interactability) entity;
                buffer.add(iteratableEntity);
            }
        }
        for (Interactability element: buffer) {
            if (element.interact(this, map)) {
                interactAll(this.getLocation(), map, p);
            }
        }
    }

    @Override
    public void movement(Position p) {
        Location curr = getLocation().clone();
        Location next = getLocation().getLocation(p);
        System.out.println(String.format("%s at %s", getType(), curr.toString()));
        this.next = p;
        interactAll(curr, map, p);
        if (getLocation().equals(curr) && !stay) {
            if (DungeonMap.isaccessible(map, next, this)) {
                previousLocation.setLocation(getLocation());
                setLocation(next);
            }
        }
        System.out.println(String.format("%s %s -> %s", getType(),previousLocation.toString(), getLocation().toString()));
    }
    public boolean pickup(Entity entity) {
        return inventory.addToInventoryList(entity, this);
    }

    public void build(String buildable, Config config) throws InvalidActionException, IllegalArgumentException {
        BuildableRecipe recipe = BuildableEntityFactory.newRecipe(buildable);
        if (recipe.CountItem(inventory.view()).isSatisfied() 
            && recipe.getPrerequisite().allMatch(map.iterator()).isSatisfied()) {
                if (recipe.getRecipeName().equals("midnight_armour")) {
                    attack.removeBounus((BonusDamageAdd) inventory.getItems("sword").get(0));
                }
            String type = recipe.removeCountItem(inventory).getItemType();
            Entity item = BuildableEntityFactory.newEntity(type, config, String.format("%s_BuildBy_%s_%d", type, getEntityId(), buildCounter));
            if (item instanceof Sceptre) {
                Sceptre sceptre = (Sceptre) item;
                this.sceptres.add(sceptre);
            } 
            inventory.addToInventoryList(item, this);
            buildCounter++;
        } else {
            throw new InvalidActionException("player does not have sufficient items to craft the buildable");
        }

    }

    public void useItem(String itemUsedId) throws InvalidActionException, IllegalArgumentException {
        Entity entity = inventory.getItem(itemUsedId);
        if (entity == null) {
            throw new InvalidActionException("itemUsed is not in the player's inventory");
        }
        if (entity instanceof Useable) {
            ((Useable) entity).use(map, this);
            notifyPotionEffectObserver();
        } else {
            throw new IllegalArgumentException(
                    "itemUsed is not a bomb, invincibility_potion, or an invisibility_potion");
        }

    }

    public PotionEntity getCurrentEffect() {
        return effects.peek();
    }

    public void updatePotionDuration() {
        Queue<PotionEntity> queue = new LinkedList<>(effects);
        for(PotionEntity effect : queue){
            if(effect.checkDurability()){
                effects.remove(effect);
                notifyPotionEffectObserver();
            }else{
                effect.setDurability();
            }
        }
    }

    public void updateSceptreRound() {
        for (Sceptre sceptre : sceptres) {
            if (sceptre.getTimerStart()){
                sceptre.setTimer();
                System.out.println("THE timer: " + sceptre.getTimer());
                if (sceptre.checkTimer()) {
                    sceptre.stopTimer();
                    // Free all the mercenary
                    notifySceptreEffectObserver();
                }
            }
        }
    }

    public boolean hasEffect() {
        return effects.size() != 0;
    }


    public Location getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation.setLocation(previousLocation);
    }

    // public List<Entity> getBattleUsage() {
    //     List<Entity> list = new ArrayList<>();
    //     if (hasEffect()) {
    //         list.add(effects.peek());
    //     }
    //     List<Entity> invUsage = inventory.getAllInventory().stream()
    //             .filter(temp -> (temp instanceof DurabilityEntity && !(temp instanceof PotionEntity)) || temp instanceof MidnightArmour)
    //             .collect(Collectors.toList());
    //     list.addAll(invUsage);
    //     return list;
    // }

    @Override
    public void attach(PotionEffectObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(PotionEffectObserver observer) {
        observers.remove(observer);

        
    }

    @Override
    public void notifyPotionEffectObserver() {
        for (PotionEffectObserver observer : observers) {
            observer.update(this);
        }
        
    }

    @Override
    public String toString() {
        String sec1 = String.format("%s(%s)  %s -*-*->%s\t", getType(), getEntityId(), previousLocation.toString(), getLocation().toString());
        String sec2 = String.format("    H: %f, A:%f D: %f", health, attack.attackDamage(), defence.defenceDamage());
        // System.out.println(String.format("   H: %f, A:%f D: %f", health, attack.attackDamage(), defence.defenceDamage()));
        return sec1 + sec2;
    }
    public void print() {
        System.out.println(toString());
    }
    public boolean canBattle(Entity entity) {
        if (entity == this) {
            return false;
        }
        
        if (hasEffect() && getCurrentEffect().applyEffect().equals("Invisibility")) {
            return false;
        }
        if (entity instanceof Player) {
            boolean sun = inventory.hasItem("sun_stone", 1);
            boolean armour = inventory.hasItem("midnight_armour", 1);
            return !(sun || armour);
        }
        return true;
    }
    //

    @Override
    public AttackStrategy getAttackStrayegy() {
        // TODO Auto-generated method stub
        return attack;
    }

    @Override
    public String getEnemyId() {
        // TODO Auto-generated method stub
        return getEntityId();
    }

    @Override
    public String getEnemyType() {
        // TODO Auto-generated method stub
        return getType();
    }

    @Override
    public void SceptreAttach(SceptreEffectObserver observer) {
        // TODO Auto-generated method stub
        this.SceptreObservers.add(observer);
    }

    @Override
    public void SceptreDetach(SceptreEffectObserver observer) {
        // TODO Auto-generated method stub
        this.SceptreObservers.remove(observer);
    }

    @Override
    public void notifySceptreEffectObserver() {
        // TODO Auto-generated method stub
        for (SceptreEffectObserver SceptreObserver : SceptreObservers) {
            System.out.println("------------------- notifyObserver -------------------");
            SceptreObserver.SceptreUpdate(this, map);
        }
    }

    // Battle
    @Override
    public boolean subHealth(double damage) {
        // TODO Auto-generated method stub
        // this.subHealth(damage);
        health -= damage;
        if (health <= 0.0002) {
            health = 0.0;
            return false;
        }
        return true;
    }

    @Override
    public boolean battleWith(Enemy enemy) {
        // TODO Auto-generated method stub
        return subHealth(battleDamageFrom(enemy));
    }

    @Override
    public double battleDamageFrom(Enemy enemy) {
        // TODO Auto-generated method stub
        AttackStrategy attackStrayegy = enemy.getAttackStrayegy();
        double damage = attackStrayegy.attackDamage() - defence.defenceDamage();
        return (damage > 0.0 ? damage : 0.0) / 10.0;
    }
    @Override
    public List<ItemResponse> getBattleUsedItems() {
        List<ItemResponse> usage = inventory.stream()
                                    .filter(entity -> (entity instanceof BonusStrategy))
                                    .map(entity -> ((BonusStrategy) entity).toItemResponse())
                                    .collect(Collectors.toList());
        if (hasEffect()) {
            PotionEntity potion = effects.peek();
            usage.add(potion.getItemResponse());
        }
        return usage;
    }
    @Override
    public boolean isAlive() {
        return getHealth() > 0.0;
    }

    @Override
    public boolean battleWith(Player player) {
        return this.battleWith((Enemy) player);
    }

    @Override
    public double battleDamageFrom(Player player) {
        return this.battleDamageFrom((Enemy) player);
    }


}
