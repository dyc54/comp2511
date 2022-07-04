package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import dungeonmania.Entity;
import dungeonmania.Strategies.BattleStrategy;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Spider extends MovingEntity implements MovementStrategy, BattleStrategy{
    private Location location;
    private ArrayList<Location> movingRecordList = new ArrayList<>(); 
    private int spider_health;
    private int spider_attack;
    public Spider(Location location, int spider_attack, int spider_health) {
        this.location = location;
        this.spider_attack = spider_attack;
        this.spider_health = spider_health;
        addToMovingList(location);
    }

    @Override
    public void movement(DungeonMap dungeonMap) {
        Location initLocation = movingRecordList.get(0);
        Location pastLocation = movingRecordList.get(movingRecordList.size() - 1);
        Collection<Entity> entities = dungeonMap.getEightNearEntities(initLocation);
        
        // Clockwise
        if (initLocation.equals(location)) {
            setLocation(Location.getUp(initLocation));
        } else if (location.equals(Location.getUp(initLocation))) {
            setLocation(Location.getTopRight(initLocation));
        } else if (location.equals(Location.getDown(initLocation))) {
            setLocation(Location.getBottomLeft(initLocation));
        } else if (location.equals(Location.getLeft(initLocation))) {
            setLocation(Location.getTopLeft(initLocation));
        } else if (location.equals(Location.getRight(initLocation))) {
            setLocation(Location.getBottomRight(initLocation));
        } else if (location.equals(Location.getTopLeft(initLocation))) {
            setLocation(Location.getUp(initLocation));
        } else if (location.equals(Location.getBottomLeft(initLocation))) {
            setLocation(Location.getLeft(initLocation));
        } else {
            setLocation(Location.getDown(initLocation));
        }

        // Anti-clockwise
        if (initLocation.equals(location)) {
            setLocation(Location.getUp(initLocation));
        } else if (location.equals(Location.getUp(initLocation))) {
            setLocation(Location.getTopLeft(initLocation));
        } else if (location.equals(Location.getDown(initLocation))) {
            setLocation(Location.getBottomRight(initLocation));
        } else if (location.equals(Location.getLeft(initLocation))) {
            setLocation(Location.getBottomLeft(initLocation));
        } else if (location.equals(Location.getRight(initLocation))) {
            setLocation(Location.getTopRight(initLocation));
        } else if (location.equals(Location.getTopLeft(initLocation))) {
            setLocation(Location.getLeft(initLocation));
        } else if (location.equals(Location.getBottomLeft(initLocation))) {
            setLocation(Location.getDown(initLocation));
        } else {
            setLocation(Location.getRight(initLocation));
        }
        
        addToMovingList(location);
    }
    
    public void addToMovingList(Location location) {
        movingRecordList.add(location);
    }
   
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void battle() {
        spider_health = spider_health - (1); // health = health - attacked
        
    }
}
