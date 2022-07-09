package dungeonmania;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.CollectableEntities.CollectableEntity;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.Strategies.PlayerMovementStrategy;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Player extends Entity implements PlayerMovementStrategy {
    private int attack;
    private int health;
    private List<Entity> inventoryList;
    private int x;
    private int y;
    private DungeonMap map;
    private Location previousLocation;

    public Player(String id,String type, int x, int y, int attack, int health, DungeonMap map) {
        this.attack = attack;
        this.health = health;
        this.x = x;
        this.y = y;
        previousLocation = Location.AsLocation(x, y);
        this.map = map;
        this.inventoryList = new ArrayList<Entity>();
        setType(type);
        setLocation(x, y);
        setEntityId(id);
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public List<Entity> getInventoryList() {
        return inventoryList;
    }

    public void addInventoryList(Entity item) {
        inventoryList.add(item);
    }

    public void removeInventoryList(Entity item) {
        inventoryList.remove(item);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<ItemResponse> getItemResponse() {
        List<ItemResponse> inventory = new ArrayList<>();
        /*
         * for(Entity item : inventoryList){
         * inventory.add(new ItemResponse(item.getEntityId(), item.getType()));
         * }
         */
        inventoryList.stream().forEach(item -> inventory.add(new ItemResponse(item.getEntityId(), item.getType())));
        return inventory;
    }

    @Override
    public void movement(Position p) {
        // If there is a wall, don't move
        if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("wall"))) {
            return;
        }

        /* // If there is a key
        if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("key"))) {
            // Delete the key from the map
            map.removeEntity(map.getEntities(x + p.getX(), y + p.getY()).stream().filter(e -> e.getType().equals("key"))
                    .findFirst().get().getEntityId());
            // Add into inventory
            this.addInventoryList(new Key("key", x, y));
            // move
            setPreviousLocation(Location.AsLocation(x, y));
            move(p);
            return;
        } */

        // If there is a door
        if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("door"))) {
            // If you have a key
            if (this.getInventoryList().stream().anyMatch(e -> e.getType().equals("key"))) {
                // remove key from the bag
                this.removeInventoryList(
                        this.getInventoryList().stream().filter(e -> e.getType().equals("key")).findFirst().get());
                // open the door
                map.getEntities(x + p.getX(), y + p.getY()).stream().filter(e -> e.getType().equals("door"))
                        .findFirst().get().setType("opened_door");
                // move to the position of door
                setPreviousLocation(Location.AsLocation(x, y));
                move(p);
            }
            
            // Don't have a key
            return;
        }

        // If there is a Exit
        if (map.getEntities(x + p.getX(), y + p.getY()).stream().anyMatch(e -> e.getType().equals("exit"))) {
            move(p);
            Entity temp = map.getEntities(x + p.getX(), y + p.getY()).stream().filter(e -> e.getType().equals("exit"))
                    .findFirst().get();
            Exit exit = (Exit) temp;
            exit.setPlayerExit(true);
        }
        move(p);
        pickUp();
    }

    public void move(Position p) {
        super.setLocation(x + p.getX(), y + p.getY());
        this.x = x + p.getX();
        this.y = y + p.getY();
    }

    //player位置存在可收集实体则放入背包，并从map中删除
    public void pickUp() {
        Collection<Entity> currentPositionEntities = map.getEntities(x, y);
        for(Entity currentPositionEntitie : currentPositionEntities){
            if(currentPositionEntitie instanceof CollectableEntity){
                addInventoryList(currentPositionEntitie);
                map.removeEntity(currentPositionEntitie.getEntityId());
            }
        }
    }
        
    public Location getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(Location previousLocation) {
        this.previousLocation = previousLocation;
    }

}
