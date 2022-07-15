package dungeonmania;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import dungeonmania.CollectableEntities.Bomb;
import dungeonmania.CollectableEntities.CollectableEntity;
import dungeonmania.CollectableEntities.Effect;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.CollectableEntities.DurabilityEntities.DurabilityEntity;
import dungeonmania.CollectableEntities.DurabilityEntities.PotionEntity;
import dungeonmania.Inventories.Inventory;
import dungeonmania.StaticEntities.Boulder;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.StaticEntities.Portal;
import dungeonmania.Strategies.PlayerMovementStrategy;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.AttackStrategies.BonusDamageStrategy;
import dungeonmania.Strategies.AttackStrategies.WeaponableAttackStrategy;
import dungeonmania.Strategies.DefenceStrategies.ArmorableStrategy;
import dungeonmania.Strategies.DefenceStrategies.DefenceStrategy;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Position;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Player extends Entity implements PlayerMovementStrategy {
    // private int attack;
    private AttackStrayegy attack;
    private DefenceStrategy defence;
    private double health;
    private Inventory inventory;
    private Position direction;
    // private int x;
    // private int y;
    private boolean stay;
    private DungeonMap map;
    private Location previousLocation;
    private Queue<PotionEntity> effects;

    public Player(String type, int x, int y, int attack, int health, DungeonMap map) {
        super(type, x, y);
        this.attack = new WeaponableAttackStrategy(attack);
        this.defence = new ArmorableStrategy(0);
        this.health = health;
        // this.x = x;
        // this.y = y;
        previousLocation = Location.AsLocation(x, y);
        inventory = new Inventory();
        this.map = map;
        stay = false;
        effects = new ArrayDeque<>();
        this.direction = new Position(0, 0);
    }

    public void addeffect(PotionEntity e) {
        effects.add(e);
    }

    public AttackStrayegy getAttackStrayegy() {
        return attack;
    }

    public DefenceStrategy getDefenceStrayegy() {
        return defence;
    }

    public double getHealth() {
        return health;
    }

    public Position getDirection() {
        return this.direction;
    }

    public void subHealth(double health) {
        this.health -= health;
    }

    public List<Entity> getInventoryList() {
        return inventory.getAllInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }
    // public void addInventoryList(Entity item) {
    // inventory.addToInventoryList(item);
    // }
    // public void useItem(String id) {

    // }
    public void removeInventoryList(Entity item) {
        inventory.removeFromInventoryList(item);
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }

    public void setDirection(Position p) {
        this.direction = p;
    }

    public boolean getStay() {
        return stay;
    }

    public List<ItemResponse> getItemResponse() {
        List<ItemResponse> inventory = new ArrayList<>();
        /*
         * for(Entity item : inventoryList){
         * inventory.add(new ItemResponse(item.getEntityId(), item.getType()));
         * }
         */
        getInventoryList().stream()
                .forEach(item -> inventory.add(new ItemResponse(item.getEntityId(), item.getType())));
        return inventory;
    }

    private void interactAll(Location curr, DungeonMap map, Position p) {
        // Location next = getLocation().getLocation(p);
        Location next = curr.getLocation(p);
        System.out.println(String.format("interact at %s", next.toString()));
        // TODO: Dealwith Concurrent Expection Error
        for (Iterator<Entity> iterator = map.getEntities(next).iterator(); iterator.hasNext();) {
            Entity entity = iterator.next();
            if (entity instanceof Interactability && entity.getLocation().equals(next)) {
                Interactability iteratableEntity = (Interactability) entity;
                if (iteratableEntity.interact(this, map)) {
                    interactAll(this.getLocation(), map, p);
                }
            }
        }
    }

    @Override
    public void movement(Position p) {
        // If there is a wall, don't move
        // List<Entity> blocked = DungeonMap.blockedEntities(map,
        // getLocation().getLocation(p), this);
        // Location temp = previousLocation.clone();
        Location curr = getLocation().clone();
        Location next = getLocation().getLocation(p);
        System.out.println(String.format("Player at %s", curr.toString()));
        setDirection(p);
        interactAll(curr, map, p);
        if (getLocation().equals(curr) && !stay) {
            if (DungeonMap.isaccessible(map, next, this)) {
                previousLocation = getLocation().clone();
                setLocation(next);
            }
        }
    }

    public boolean pickup(Entity entity) {
        if (entity instanceof Key) {
            if (getInventoryList().stream().anyMatch(e -> e instanceof Key)) {
                return false;
            }
        }
        return inventory.addToInventoryList(entity, this);
    }

    // player 查询背包物品进行建造
    public String build(String buildable, Config config) throws IllegalArgumentException, InvalidActionException {
        switch (buildable) {
            case "bow":
                boolean wood_b = inventory.hasItem("wood", 1);
                boolean arrow = inventory.hasItem("arrows", 3);
                if (wood_b && arrow) {
                    inventory.addToInventoryList(EntityFactory.newEntity(buildable, config), this);
                } else {
                    throw new InvalidActionException("player does not have sufficient items to craft the buildable");
                }
                inventory.removeFromInventoryList("wood", 1);
                inventory.removeFromInventoryList("arrows", 3);
                break;
            case "shield":
                boolean wood_s = inventory.hasItem("wood", 2);
                boolean treasure = inventory.hasItem("treasure ", 1);
                boolean key = inventory.hasItem("key ", 1);
                if (wood_s && (treasure || key)) {
                    inventory.addToInventoryList(EntityFactory.newEntity(buildable, config), this);
                } else {
                    throw new InvalidActionException("buildable is not one of bow, shield");
                }
                inventory.removeFromInventoryList("wood", 2);
                if (treasure) {
                    inventory.removeFromInventoryList("treasure", 1);
                } else {
                    inventory.removeFromInventoryList("key", 1);
                }
                break;
            default:
                throw new IllegalArgumentException("buildable is not one of bow, shield");
            // break;
        }
        return "";
    }

    // player 使用物品
    public void useItem(String itemUsedId) throws InvalidActionException, IllegalArgumentException {
        Entity entity = inventory.getItem(itemUsedId);
        if (entity == null) {
            throw new InvalidActionException("itemUsed is not in the player's inventory");
        }
        if (entity instanceof PotionEntity) {
            addeffect((PotionEntity) entity);
            inventory.removeFromInventoryList(entity);
        } else if (entity instanceof Bomb) {
            Bomb bomb = (Bomb) entity;
            // if bomb
            bomb.put(getLocation(), map);
            // map.addEntity(bomb);
            inventory.removeFromInventoryList(entity);
        } else {
            throw new IllegalArgumentException(
                    "itemUsed is not a bomb, invincibility_potion, or an invisibility_potion");
        }

        // inventory.useItem(itemUsedId);
    }

    // //查询player状态，无敌还是隐身，还是都有
    // public List<String> checkPlayerStatus(){
    // return inventory.checkPlayerStatus();
    // }
    public PotionEntity getCurrentEffect() {
        return effects.peek();
    }

    public boolean hasEffect() {
        return effects.size() != 0;
    }

    public Location getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation = previousLocation;
    }

    public void update() {
        PotionEntity entity = getCurrentEffect();
        entity.setDurability();
        if (!entity.checkDurability()) {
            effects.poll();
        }
        // TODO: refactor
        for (Iterator<Entity> iterator = inventory.getAllInventory().iterator(); iterator.hasNext();) {
            Entity temp = iterator.next();
            if (temp instanceof DurabilityEntity && !(temp instanceof PotionEntity)) {
                DurabilityEntity durabilityEntity = (DurabilityEntity) temp;
                durabilityEntity.setDurability();
                if (!entity.checkDurability()) {
                    inventory.removeFromInventoryList(temp);
                }
            }
        }

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
    // TODO: if player have sword, bow or bribed mercenary, attack has to be added.
    // TODO: e.g. attack.bonusDamage(sward)

    //
}
