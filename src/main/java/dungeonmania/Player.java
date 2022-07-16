package dungeonmania;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import dungeonmania.CollectableEntities.Bomb;
import dungeonmania.CollectableEntities.DurabilityEntities.DurabilityEntity;
import dungeonmania.CollectableEntities.DurabilityEntities.PotionEntity;
import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.BuildableRecipe;
import dungeonmania.Inventories.Inventory;
import dungeonmania.Strategies.PlayerMovementStrategy;
import dungeonmania.Strategies.AttackStrategies.AttackStrategy;
import dungeonmania.Strategies.AttackStrategies.WeaponableAttackStrategy;
import dungeonmania.Strategies.DefenceStrategies.ArmorableStrategy;
import dungeonmania.Strategies.DefenceStrategies.DefenceStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Player extends Entity implements PlayerMovementStrategy, PotionEffectSubject {
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
    private List<PotionEffecObserver> observers;
    public Player(String type, int x, int y, int attack, int health, DungeonMap map) {
        super(type, x, y);
        this.attack = new WeaponableAttackStrategy(attack);
        this.defence = new ArmorableStrategy(0);
        this.health = health;
        previousLocation = Location.AsLocation(x, y);
        inventory = new Inventory();
        this.map = map;
        stay = false;
        observers = new ArrayList<>();
        effects = new ArrayDeque<>();
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
            notifyObserver();
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
        System.out.println(String.format("Player at %s", curr.toString()));
        this.next = p;
        interactAll(curr, map, p);
        if (getLocation().equals(curr) && !stay) {
            if (DungeonMap.isaccessible(map, next, this)) {
                previousLocation.setLocation(getLocation());
                setLocation(next);
            }
        }
        System.out.println(String.format("Player %s -> %s", previousLocation.toString(), getLocation().toString()));
    }
    public boolean pickup(Entity entity) {
        return inventory.addToInventoryList(entity, this);
    }

    public void build(String buildable, Config config) throws InvalidActionException, IllegalArgumentException {
        BuildableRecipe recipe = BuildableEntityFactory.newRecipe(buildable);
        if (recipe.isSatisfied(inventory)) {
            String type = recipe.consumeMaterial(inventory).getRecipeName();
            inventory.addToInventoryList(BuildableEntityFactory.newEntity(type, config), this);
        } else {
            throw new InvalidActionException("player does not have sufficient items to craft the buildable");
        }

    }

    public void useItem(String itemUsedId) throws InvalidActionException, IllegalArgumentException {
        Entity entity = inventory.getItem(itemUsedId);
        if (entity == null) {
            throw new InvalidActionException("itemUsed is not in the player's inventory");
        }
        if (entity instanceof PotionEntity) {
            addeffect((PotionEntity) entity);
            notifyObserver();
            inventory.removeFromInventoryList(entity);
        } else if (entity instanceof Bomb) {
            Bomb bomb = (Bomb) entity;
            bomb.put(getLocation(), map);
            inventory.removeFromInventoryList(entity);
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
            }else{
                effect.setDurability();
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
    public List<Entity> getBattleUsage() {
        List<Entity> list = new ArrayList<>();
        if (hasEffect()) {
            list.add(effects.peek());
        }
        List<Entity> invUsage = inventory.getAllInventory().stream()
                .filter(temp -> temp instanceof DurabilityEntity && !(temp instanceof PotionEntity))
                .collect(Collectors.toList());
        list.addAll(invUsage);
        return list;
    }

    @Override
    public void attach(PotionEffecObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(PotionEffecObserver observer) {
        observers.remove(observer);

        
    }

    @Override
    public void notifyObserver() {
        for (PotionEffecObserver observer : observers) {
            observer.update(this);
        }
        
    }

    //
}
