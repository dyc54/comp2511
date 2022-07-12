package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Strategies.BattleStrategy;
import dungeonmania.Strategies.EnemyMovementStrategy;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

import java.util.Random;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ZombieToast extends MovingEntity implements EnemyMovementStrategy, BattleStrategy {
    Location location;
    
    public ZombieToast(String type, Location location, int zombie_attack, int zombie_health) {
        super(new BaseAttackStrategy(zombie_attack));
        this.location = location;
        setType(type);
    }

    @Override
    public void movement(DungeonMap dungeonMap) {
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
     * 
     * @param fourDirection
     * @param randomDirection
     */
    public void updateLocation(Map<Integer, Entity> fourDirection, int randomDirection) {
        if (randomDirection == 0
                && !fourDirection.get(0).getType().equals("wall")
                && !fourDirection.get(0).getType().equals("boulder")
                && !fourDirection.get(0).getType().equals("door")) {
            setLocation(Location.getUp(location));
        } else if (randomDirection == 1
                && !fourDirection.get(1).getType().equals("wall")
                && !fourDirection.get(1).getType().equals("boulder")
                && !fourDirection.get(1).getType().equals("door")) {
            setLocation(Location.getDown(location));
        } else if (randomDirection == 2
                && !fourDirection.get(2).getType().equals("wall")
                && !fourDirection.get(2).getType().equals("boulder")
                && !fourDirection.get(2).getType().equals("door")) {
            setLocation(Location.getLeft(location));
        } else if (randomDirection == 3
                && !fourDirection.get(3).getType().equals("wall")
                && !fourDirection.get(3).getType().equals("boulder")
                && !fourDirection.get(3).getType().equals("door")) {
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
