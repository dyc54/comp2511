package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.Movement;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

import java.util.Map;

public class ZombieToast extends MovingEntity implements EnemyMovement, Enemy {
    // Location location;
    
    public ZombieToast(String type, Location location, int zombie_attack, double zombie_health) {
        super(type, location, zombie_health, new BaseAttackStrategy(zombie_attack), new RandomMovement());
    }

    private String nextPossibleLocation(DungeonMap dungeonMap) {
        return MovingEntity.getPossibleNextDirection(dungeonMap, this);
    }
    @Override
    public boolean movement(DungeonMap dungeonMap) {

        String choice = nextPossibleLocation(dungeonMap);
        System.out.println(choice);
        Location next = getMove().MoveOptions(choice).nextLocation(getLocation());
        if (next.equals(getLocation())) {
            return false;
        } else {
            setLocation(next);
            dungeonMap.UpdateEntity(this);
            return true;
        }
        // Collection<Entity> fourNearEntities = dungeonMap.getFourNearEntities(getLocation());
        // Map<Integer, Entity> fourDirection = new HashMap<>();
        // for (Entity entity : fourNearEntities) {
        //     if (entity.getLocation().equals(getLocation().getUp())) {
        //         fourDirection.put(0, entity);
        //     } else if (entity.getLocation().equals(getLocation().getDown())) {
        //         fourDirection.put(1, entity);
        //     } else if (entity.getLocation().equals(getLocation().getLeft())) {
        //         fourDirection.put(2, entity);
        //     } else {
        //         fourDirection.put(3, entity);
        //     }
        // }
        // int randomDirection = new Random().nextInt(4);
        // updateLocation(fourDirection, randomDirection);
    }

    
    // public void updateLocation(Map<Integer, Entity> fourDirection, int randomDirection) {
    //     if (randomDirection == 0
    //             && !fourDirection.get(0).getType().equals("wall")
    //             && !fourDirection.get(0).getType().equals("boulder")
    //             && !fourDirection.get(0).getType().equals("door")) {
    //         setLocation(getLocation().getUp());
    //     } else if (randomDirection == 1
    //             && !fourDirection.get(1).getType().equals("wall")
    //             && !fourDirection.get(1).getType().equals("boulder")
    //             && !fourDirection.get(1).getType().equals("door")) {
    //         setLocation(getLocation().getDown());
    //     } else if (randomDirection == 2
    //             && !fourDirection.get(2).getType().equals("wall")
    //             && !fourDirection.get(2).getType().equals("boulder")
    //             && !fourDirection.get(2).getType().equals("door")) {
    //         setLocation(getLocation().getLeft());
    //     } else if (randomDirection == 3
    //             && !fourDirection.get(3).getType().equals("wall")
    //             && !fourDirection.get(3).getType().equals("boulder")
    //             && !fourDirection.get(3).getType().equals("door")) {
    //         setLocation(getLocation().getRight());
    //     }
    // }

    @Override
    public AttackStrayegy getAttackStrayegy() {
        return super.getAttack();
    }

    @Override
    public String getEnemyId() {
        return getEntityId();
    }
    @Override
    public double getHealth() {
        return super.getHealth();
    }
    @Override
    public String getEnemyType() {
        return getType();
    }
   
}
