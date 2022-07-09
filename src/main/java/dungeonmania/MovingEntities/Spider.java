package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    /**
     * Constructor for spider class
     * @param location
     * @param spider_attack
     * @param spider_health
     */
    public Spider(String type, Location location, int spider_attack, int spider_health) {
        this.location = location;
        this.spider_attack = spider_attack;
        this.spider_health = spider_health;
        setType(type);
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

    /**
     * Using pastLocation and initLocation to judge the spider moving direction whether clockwise
     * @param pastLocation
     * @param initLocation
     * @return boolean
     */
    public boolean is_clockwise(Location pastLocation, Location initLocation) {
        return (location.equals(Location.getUp(pastLocation)) && (location.equals(Location.getLeft(initLocation)) || (location.equals(Location.getTopLeft(initLocation))))
        || location.equals(Location.getDown(pastLocation)) && (location.equals(Location.getRight(initLocation)) || (location.equals(Location.getBottomRight(initLocation))))
        || location.equals(Location.getLeft(pastLocation)) && (location.equals(Location.getDown(initLocation)) || (location.equals(Location.getBottomLeft(initLocation))))
        || location.equals(Location.getRight(pastLocation)) && (location.equals(Location.getUp(initLocation)) || (location.equals(Location.getTopRight(initLocation)))));
    }
    
    /**
     * Find the clockwise location for the spider
     * @param initLocation
     * @return Location
     */
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
        } else if (location.equals(Location.getTopRight(initLocation))) {
            return Location.getRight(initLocation);
        } else if (location.equals(Location.getBottomLeft(initLocation))) {
            return Location.getLeft(initLocation);
        } else {
            return Location.getDown(initLocation);
        }
    }

    /**
     * Find the anticlockwise Location for the spider
     * @param initLocation
     * @return Location
     */
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
        } else if (location.equals(Location.getTopRight(initLocation))) {
            return Location.getUp(initLocation);
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
