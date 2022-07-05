package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Strategies.BattleStrategy;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

import java.util.Random;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ZombieToast extends MovingEntity implements MovementStrategy, BattleStrategy{
    Location location;
    
    public ZombieToast(Location location, int zombie_attack, int zombie_health) {
        this.location = location;
    }

    @Override
    public void movement(DungeonMap dungeonMap) {
        // TODO Auto-generated method stub
        Collection<Entity> fourNearEntities = dungeonMap.getFourNearEntities(location);
        Map<Integer, Entity> fourDirection = new HashMap<>();
        for (Entity entity : fourNearEntities) {
            if (entity.getLocation().equals(Location.getUp(location))) {
                fourDirection.put(0, entity);
            } else if (entity.getLocation().equals(Location.getDown(location))) {
                fourDirection.put(1, entity);
            } else if (entity.getLocation().equals(Location.getLeft(location))) {
                fourDirection.put(2, entity);
            } else {
                fourDirection.put(3, entity);
            }
        }
        int randomDirection = new Random().nextInt(4);
        updateLocation(fourDirection, randomDirection);
        dungeonMap.UpdateEntity(this);
    }

    /**
     * update the zombie location
     * @param fourDirection
     * @param randomDirection
     */
    public void updateLocation(Map<Integer, Entity> fourDirection, int randomDirection) {
        if (randomDirection == 0 
        && !fourDirection.get(0).getType().equals("Wall") 
        && !fourDirection.get(0).getType().equals("Boulder") 
        && !fourDirection.get(0).getType().equals("Door")) {
            setLocation(Location.getUp(location));
        } else if (randomDirection == 1 
        && !fourDirection.get(1).getType().equals("Wall") 
        && !fourDirection.get(1).getType().equals("Boulder") 
        && !fourDirection.get(1).getType().equals("Door")) {
            setLocation(Location.getDown(location));
        } else if (randomDirection == 2 
        && !fourDirection.get(2).getType().equals("Wall") 
        && !fourDirection.get(2).getType().equals("Boulder") 
        && !fourDirection.get(2).getType().equals("Door")) {
            setLocation(Location.getLeft(location));
        } else if (randomDirection == 3 
        && !fourDirection.get(3).getType().equals("Wall") 
        && !fourDirection.get(3).getType().equals("Boulder") 
        && !fourDirection.get(3).getType().equals("Door")){
            setLocation(Location.getRight(location));
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
