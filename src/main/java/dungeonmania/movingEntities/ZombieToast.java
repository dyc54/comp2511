package dungeonmania.movingEntities;

import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.EnemyMovement;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.movementStrategies.MovementOptions;
import dungeonmania.strategies.movementStrategies.RandomMovement;


public class ZombieToast extends MovingEntity implements EnemyMovement, Enemy {
    
    public ZombieToast(String type, Location location, int zombie_attack, double zombie_health) {
        super(type, location, zombie_health, new BaseAttackStrategy(zombie_attack), new RandomMovement());
    }

    // private String nextPossibleLocation(DungeonMap dungeonMap) {
    //     return MovingEntity.getPossibleNextDirection(dungeonMap, this);
    // }
    @Override
    public boolean movement(DungeonMap dungeonMap) {

        String choice = MovementOptions.encodeLocationsArguments(dungeonMap, this);
        System.out.println(choice);
        Location next = getMove().MoveOptions(choice).nextLocation(getLocation(), dungeonMap);
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
