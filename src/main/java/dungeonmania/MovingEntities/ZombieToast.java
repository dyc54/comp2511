package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Strategies.BattleStrategy;
import dungeonmania.Strategies.EnemyMovementStrategy;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

import java.util.Random;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZombieToast extends MovingEntity implements EnemyMovementStrategy, BattleStrategy {
    Location location;
    
    public ZombieToast(String id, String type, Location location, int zombie_attack, int zombie_health) {
        this.location = location;
        setType(type);
        setEntityId(id);
    }

    @Override
    public void movement(DungeonMap dungeonMap) {
        Collection<Entity> fourNearEntities = dungeonMap.getFourNearEntities(location);
        Map<Integer, String> fourDirection = new HashMap<>();
        for (Entity entity : fourNearEntities) {
            if (entity.getLocation().equals(Location.getUp(location))) {
                fourDirection.put(0, entity.getType());
            } else if (entity.getLocation().equals(Location.getDown(location))) {
                fourDirection.put(1, entity.getType());
            } else if (entity.getLocation().equals(Location.getLeft(location))) {
                fourDirection.put(2, entity.getType());
            } else {
                fourDirection.put(3, entity.getType());
            }
        }
        int randomDirection = new Random().nextInt(4);
        updateLocation(fourDirection, randomDirection);
        dungeonMap.UpdateEntity(this);
    }

    /**
     * update the zombie location
     * 
     * @param fourDirection
     * @param randomDirection
     */
    public void updateLocation(Map<Integer, String> fourDirection, int randomDirection) {
        System.out.println(fourDirection);
        System.out.println("random:" + randomDirection);
        List<String> list = new ArrayList<>();
        list.add("wall");
        list.add("boulder");
        list.add("door");
        if (randomDirection == 0) {
            if (!fourDirection.keySet().contains(0)) {
                setLocation(Location.getUp(location));
            } else if (fourDirection.keySet().contains(0) && !list.contains(fourDirection.get(0))) {
                setLocation(Location.getUp(location));
            }  
        } else if (randomDirection == 1) {
            if (!fourDirection.keySet().contains(1)) {
                setLocation(Location.getDown(location));
            } else if (fourDirection.keySet().contains(1) && !list.contains(fourDirection.get(1))) {
                setLocation(Location.getDown(location));
            } 
        } else if (randomDirection == 2) {
            if (!fourDirection.keySet().contains(2)) {
                setLocation(Location.getLeft(location));
            } else if (fourDirection.keySet().contains(2) && !list.contains(fourDirection.get(2))) {
                setLocation(Location.getLeft(location));
            }  
        } else if (randomDirection == 3) {
            if (!fourDirection.keySet().contains(3)) {
                setLocation(Location.getRight(location));
            } else if (fourDirection.keySet().contains(3) && !list.contains(fourDirection.get(3))) {
                setLocation(Location.getRight(location));
            }  
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void battle() {
        // TODO Auto-generated method stub

    }

}
