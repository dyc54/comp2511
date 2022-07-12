package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.EnemyMovementStrategy;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Spider extends MovingEntity implements EnemyMovementStrategy, Enemy {
    // private Location location;
    private ArrayList<Location> movingRecordList = new ArrayList<>();
    // private int spider_health;
    // private int spider_attack;
    // private AttackStrayegy spider_attack;

    /**
     * Constructor for spider class
     * 
     * @param location
     * @param spider_attack
     * @param spider_health
     */
    public Spider(String type, Location location, int spider_attack, int spider_health) {
        super(type, location, spider_health, new BaseAttackStrategy(spider_attack));
        // this.location = location;
        // this.spider_attack = new BaseAttackStrategy(spider_attack);
        // this.spider_health = spider_health;
        setType(type);
        addToMovingList(location);
    }

    @Override
    public void movement(DungeonMap dungeonMap) {
        Location initLocation = movingRecordList.get(0);
        Location pastLocation;
        if (movingRecordList.size() < 2) {
            pastLocation = initLocation;
        } else {
            pastLocation = movingRecordList.get(movingRecordList.size() - 2);
        }
        Collection<Entity> entities = dungeonMap.getEightNearEntities(initLocation);
        List<Entity> boulderList = new ArrayList<>();

        for (Entity entity : entities) {
            if (entity.getType().equals("boulder")) {
                boulderList.add(entity);
            }
        }
        if (initLocation.equals(getLocation())) {
            for (Entity boulder: boulderList) {
                if (boulder.getLocation().equals(getLocation().getUp())) {
                    setLocation(initLocation.getDown());
                }
            }
            if (getLocation().equals(initLocation)) {
                setLocation(Location.getUp(initLocation));
            }
            dungeonMap.UpdateEntity(this);
            addToMovingList(getLocation());
            return;
        }
        if (boulderList.isEmpty()) {
            setLocation(clockwiseMove(initLocation));
        } else if (is_clockwise(pastLocation, initLocation)) {
            Location newLocation = clockwiseMove(initLocation);
            for (Entity entity : boulderList) {
                if (entity.getLocation().equals(newLocation)) {
                    newLocation = anticlockwiseMove(initLocation);
                    break;
                }
            }
            setLocation(newLocation);
        } else if (!is_clockwise(pastLocation, initLocation)) {
            Location newLocation = anticlockwiseMove(initLocation);
            for (Entity entity : boulderList) {
                if (entity.getLocation().equals(newLocation)) {
                    newLocation = clockwiseMove(initLocation);
                    break;
                }
            }
            setLocation(newLocation);
        }
        dungeonMap.UpdateEntity(this);
        addToMovingList(getLocation());
    }

    /**
     * Using pastLocation and initLocation to judge the spider moving direction
     * whether clockwise
     * 
     * @param pastLocation
     * @param initLocation
     * @return boolean
     */
    public boolean is_clockwise(Location pastLocation, Location initLocation) {
        return ((getLocation().equals(Location.getUp(pastLocation))
                    && (getLocation().equals(Location.getLeft(initLocation)) || (getLocation().equals(Location.getTopLeft(initLocation)))))
                || (getLocation().equals(Location.getDown(pastLocation)) 
                    && (getLocation().equals(Location.getRight(initLocation)) || (getLocation().equals(Location.getBottomRight(initLocation)))))
                || (getLocation().equals(Location.getLeft(pastLocation)) 
                    && (getLocation().equals(Location.getDown(initLocation)) || (getLocation().equals(Location.getBottomLeft(initLocation)))))
                || (getLocation().equals(Location.getRight(pastLocation)) 
                    && (getLocation().equals(Location.getUp(initLocation)) || (getLocation().equals(Location.getTopRight(initLocation)))))
                || (pastLocation.equals(initLocation)));
    }

    /**
     * Find the clockwise location for the spider
     * 
     * @param initLocation
     * @return Location
     */
    public Location clockwiseMove(Location initLocation) {
        if (getLocation().equals(Location.getUp(initLocation))) {
            return Location.getTopRight(initLocation);
        } else if (getLocation().equals(Location.getDown(initLocation))) {
            return Location.getBottomLeft(initLocation);
        } else if (getLocation().equals(Location.getLeft(initLocation))) {
            return Location.getTopLeft(initLocation);
        } else if (getLocation().equals(Location.getRight(initLocation))) {
            return Location.getBottomRight(initLocation);
        } else if (getLocation().equals(Location.getTopLeft(initLocation))) {
            return Location.getUp(initLocation);
        } else if (getLocation().equals(Location.getTopRight(initLocation))) {
            return Location.getRight(initLocation);
        } else if (getLocation().equals(Location.getBottomLeft(initLocation))) {
            return Location.getLeft(initLocation);
        } else {
            return Location.getDown(initLocation);
        }
    }

    /**
     * Find the anticlockwise Location for the spider
     * 
     * @param initLocation
     * @return Location
     */
    public Location anticlockwiseMove(Location initLocation) {
        if (getLocation().equals(Location.getUp(initLocation))) {
            return Location.getTopLeft(initLocation);
        } else if (getLocation().equals(Location.getDown(initLocation))) {
            return Location.getBottomRight(initLocation);
        } else if (getLocation().equals(Location.getLeft(initLocation))) {
            return Location.getBottomLeft(initLocation);
        } else if (getLocation().equals(Location.getRight(initLocation))) {
            return Location.getTopRight(initLocation);
        } else if (getLocation().equals(Location.getTopLeft(initLocation))) {
            return Location.getLeft(initLocation);
        } else if (getLocation().equals(Location.getTopRight(initLocation))) {
            return Location.getUp(initLocation);
        } else if (getLocation().equals(Location.getBottomLeft(initLocation))) {
            return Location.getDown(initLocation);
        } else {
            return Location.getRight(initLocation);
        }
    }

    public void addToMovingList(Location location) {
        movingRecordList.add(location);
    }

    @Override
    public AttackStrayegy getAttackStrayegy() {
        // TODO Auto-generated method stub
        return super.getAttack();
    }

    @Override
    public double getHealth() {
        // TODO Auto-generated method stub
        return super.getHealth();
    }

    @Override
    public String getEnemyId() {
        // TODO Auto-generated method stub
        return getEntityId();
    }


}
