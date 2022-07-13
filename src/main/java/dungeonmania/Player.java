package dungeonmania;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import dungeonmania.CollectableEntities.CollectableEntity;
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
    // private int x;
    // private int y;
    private boolean stay;
    private DungeonMap map;
    private Location previousLocation;

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
    public void subHealth(double health) {
        this.health -= health;
    }
    public List<Entity> getInventoryList() {
        return inventory.getInventoryList();
    }
    public Inventory getInventory() {
        return inventory;
    }
    public void addInventoryList(Entity item) {
        inventory.addToInventoryList(item);
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
        getInventoryList().stream().forEach(item -> inventory.add(new ItemResponse(item.getEntityId(), item.getType())));
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
        // List<Entity> blocked = DungeonMap.blockedEntities(map, getLocation().getLocation(p), this);
        
        
        
        // Location temp = previousLocation.clone();
        Location curr = getLocation().clone();
        Location next = getLocation().getLocation(p);
        System.out.println(String.format("Player at %s", curr.toString()));
        interactAll(curr, map, p);
        if (getLocation().equals(curr) && !stay) {
            if (DungeonMap.isaccessible(map, next, this)) {
                previousLocation = getLocation().clone();
                setLocation(next);

            }
        }
        // map.getEntities(next).stream().forEach(entity -> {
            //     if (DungeonMap.isaccessible(map, next, this)) {
            //         previousLocation = getLocation().clone();
            //         setLocation(next);
            //     }
            // });
        // // if (getLocation().equals(curr)) {

        // // }
        // previousLocation.setLocation(temp);
        // if (!getLocation().equals(curr)) {
        //     setLocation(curr);
        //     // previousLocation.setLocation(temp);
        //     if (DungeonMap.isaccessible(map, next, this)) {
        //         // previousLocation.setLocation(temp);
        //         previousLocation = getLocation().clone();
        //         setLocation(next);
        //     }
        // } else {
        //     setLocation(curr);
        // }
        // // previousLocation.setLocation(temp);
        // // Get back
        // // if (DungeonMap.isaccessible(map, next, this)) {
            //     previousLocation = getLocation().clone();
        // }
        
    }
        // if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("wall"))) {
        //     return;
        // // }

        // // If there is a door
        // if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("door"))) {
        //     // If you have a key
        //     if (this.getInventoryList().stream().anyMatch(e -> e.getType().equals("key"))) {
        //         // remove key from the bag
        //         this.removeInventoryList(
        //                 this.getInventoryList().stream().filter(e -> e.getType().equals("key")).findFirst().get());
        //         // open the door
        //         map.getEntities(x + p.getX(), y + p.getY()).stream().filter(e -> e.getType().equals("door"))
        //                 .findFirst().get().setType("opened_door");
        //         // move to the position of door
        //         setPreviousLocation(Location.AsLocation(x, y));
        //         move(p);
        //     }

        //     // Don't have a key
        //     return;
        // }

        // // // If there is a Exit
        // // if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("exit"))) {
        // //     move(p);
        // //     Entity temp = map.getEntities(x + p.getX(), y + p.getY()).stream().filter(e -> e.getType().equals("exit"))
        // //             .findFirst().get();
        // //     Exit exit = (Exit) temp;
        // //     exit.setPlayerExit(true);
        // // }

        // // If there is a boulder
        // if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("boulder"))) {
        //     // If there is a boulder or a wall or a closed door after the boulder, you
        //     // cannot move the boulder
        //     Collection<Entity> entitiesAfterBoulder = map.getEntities(x + p.getX() + p.getX(), y + p.getY() + p.getY());
        //     if (entitiesAfterBoulder.stream().anyMatch(e -> e.getType().equals("boulder"))
        //             || entitiesAfterBoulder.stream().anyMatch(e -> e.getType().equals("wall"))
        //             || entitiesAfterBoulder.stream().anyMatch(e -> e.getType().equals("door"))) {
        //         return;
        //     } else {
        //         // Else move the boulder to the next position
        //         Entity temp = map.getEntities(x + p.getX(), y + p.getY()).stream()
        //                 .filter(e -> e.getType().equals("boulder"))
        //                 .findFirst().get();
        //         Boulder boulder = (Boulder) temp;
        //         boulder.moveBoulder(p, map);
        //     }
        // }

        // // If there is a portal
        // if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("portal"))) {
        //     // Get the portal
        //     Entity temp = map.getEntities(x + p.getX(), y + p.getY()).stream().filter(e -> e.getType().equals("portal"))
        //             .findFirst().get();
        //     Portal portal = (Portal) temp;
        //     teleport(p, x + p.getX(), y + p.getY(), portal);
        //     return;
        // }
        // // Move the player
        // move(p);
        // pickUp();
    // }

    // private void teleport(Position p, int currentX, int currentY, Portal portal) {
    //     String colour = portal.getColour();
    //     // Find another portal
    //     for (Entity entity : map.getEntities("portal")) {
    //         Portal currentPortal = (Portal) entity;
    //         // Find another portal with the same colour
    //         if (currentPortal.getColour().equals(colour)
    //                 && currentPortal.getLocation().getX() != currentX
    //                 && currentPortal.getLocation().getY() != currentY) {
    //             Collection<Entity> entitiesAfterPortal = map.getEntities(
    //                     currentPortal.getLocation().getX() + p.getX(),
    //                     currentPortal.getLocation().getY() + p.getY());
    //             // If there is a obstacle, don't move
    //             if (entitiesAfterPortal.stream().anyMatch(e -> e.getType().equals("boulder"))
    //                     || entitiesAfterPortal.stream().anyMatch(e -> e.getType().equals("wall"))
    //                     || entitiesAfterPortal.stream().anyMatch(e -> e.getType().equals("door"))) {
    //                 return;
    //             } else if (entitiesAfterPortal.stream().anyMatch(e -> e.getType().equals("portal"))) {
    //                 // Else if there is another teleport
    //                 Entity temp = map.getEntities(currentPortal.getLocation().getX() + p.getX(),
    //                         currentPortal.getLocation().getY() + p.getY()).stream()
    //                         .filter(e -> e.getType().equals("portal"))
    //                         .findFirst().get();
    //                 Portal portalConnected = (Portal) temp;
    //                 teleport(p, currentPortal.getLocation().getX() + p.getX(),
    //                         currentPortal.getLocation().getY() + p.getY(),
    //                         portalConnected);
    //                 // }
    //                 return;
    //             } else {
    //                 // Else move to the related position after the portal
    //                 super.setLocation(currentPortal.getLocation().getX() + p.getX(),
    //                         currentPortal.getLocation().getY() + p.getY());
    //                 this.x = currentPortal.getLocation().getX() + p.getX();
    //                 this.y = currentPortal.getLocation().getY() + p.getY();
    //                 return;
    //             }
    //         }
    //     }
    // }

    // public void move(Position p) {
    //     super.setLocation(x + p.getX(), y + p.getY());
    //     this.x = x + p.getX();
    //     this.y = y + p.getY();
    // }

    // // player位置存在可收集实体则放入背包，并从map中删除
    // public void pickUp() {
    //     Collection<Entity> currentPositionEntities = map.getEntities(x, y);
    //     for (Entity currentPositionEntitie : currentPositionEntities) {
    //         if (currentPositionEntitie instanceof CollectableEntity) {
    //             inventory.addToInventoryList(currentPositionEntitie);
    //             map.removeEntity(currentPositionEntitie.getEntityId());
    //         }
    //     }
    // }

    public void pickup(Entity entity) {
        inventory.addToInventoryList(entity);

    }

    //player 查询背包物品进行建造
    public String build(String buildable, Config config){
        return inventory.build(buildable, config);
    }


    //player 使用物品
    public void useItem(String itemUsedId){
        inventory.useItem(itemUsedId);
    }

    //查询player状态，无敌还是隐身，还是都有
    public List<String> checkPlayerStatus(){
        return inventory.checkPlayerStatus();
    }


    public Location getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation = previousLocation;
    }
    // TODO: if player have sword, bow or bribed mercenary, attack has to be added. 
    // TODO: e.g. attack.bonusDamage(sward)

    // 
}
