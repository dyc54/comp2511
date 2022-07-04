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
        List<Entity> boulerList = new ArrayList<>();
        
        for (Entity entity : entities) {
            if (entity.getType().equals("Boulder")) {
                boulerList.add(entity);
            }
        }
        if (boulerList.isEmpty()) {
            setLocation(clockwiseMove(initLocation));
        } else if (is_clockwise(pastLocation, initLocation)) {
            Location newLocation = clockwiseMove(initLocation);
            for (Entity entity : boulerList) {
                if (entity.getLocation().equals(newLocation)) {
                    newLocation = anticlockwiseMove(initLocation);
                    break;
                }
            }
            setLocation(newLocation);
        }
        
        dungeonMap.UpdateEntity(this);
        addToMovingList(location);
    }

    public boolean is_clockwise(Location pastLocation, Location initLocation) {
        return (location.equals(Location.getUp(pastLocation)) && (location.equals(Location.getLeft(initLocation)) || (location.equals(Location.getTopLeft(initLocation))))
        || location.equals(Location.getDown(pastLocation)) && (location.equals(Location.getRight(initLocation)) || (location.equals(Location.getBottomRight(initLocation))))
        || location.equals(Location.getLeft(pastLocation)) && (location.equals(Location.getDown(initLocation)) || (location.equals(Location.getBottomLeft(initLocation))))
        || location.equals(Location.getRight(pastLocation)) && (location.equals(Location.getUp(initLocation)) || (location.equals(Location.getTopRight(initLocation)))));
    }
    
    public Location clockwiseMove(Location initLocation) {
        if (initLocation.equals(location)) {
            return Location.getUp(initLocation);
        } else if (location.equals(Location.getUp(initLocation))) {
            return Location.getTopRight(initLocation);
        } else if (location.equals(Location.getDown(initLocation))) {
            return Location.getBottomLeft(initLocation);
        } else if (location.equals(Location.getLeft(initLocation))) {
            return Location.getTopLeft(initLocation);
        } else if (location.equals(Location.getRight(initLocation))) {
            return Location.getBottomRight(initLocation);
        } else if (location.equals(Location.getTopLeft(initLocation))) {
            return Location.getUp(initLocation);
        } else if (location.equals(Location.getBottomLeft(initLocation))) {
            return Location.getLeft(initLocation);
        } else {
            return Location.getDown(initLocation);
        }
    }

    public Location anticlockwiseMove(Location initLocation) {
        if (initLocation.equals(location)) {
            return Location.getUp(initLocation);
        } else if (location.equals(Location.getUp(initLocation))) {
            return Location.getTopLeft(initLocation);
        } else if (location.equals(Location.getDown(initLocation))) {
            return Location.getBottomRight(initLocation);
        } else if (location.equals(Location.getLeft(initLocation))) {
            return Location.getBottomLeft(initLocation);
        } else if (location.equals(Location.getRight(initLocation))) {
            return Location.getTopRight(initLocation);
        } else if (location.equals(Location.getTopLeft(initLocation))) {
            return Location.getLeft(initLocation);
        } else if (location.equals(Location.getBottomLeft(initLocation))) {
            return Location.getDown(initLocation);
        } else {
            return Location.getRight(initLocation);
        }
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
