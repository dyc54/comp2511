package dungeonmania.MovingEntities;

import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.AttackStrategy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;


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
    }

    @Override
    public AttackStrategy getAttackStrayegy() {
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
