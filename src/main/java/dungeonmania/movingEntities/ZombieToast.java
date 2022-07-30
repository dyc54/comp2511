package dungeonmania.movingEntities;

import dungeonmania.MovementFactor;
import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.EnemyMovement;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.movementStrategies.MovementOptions;
import dungeonmania.strategies.movementStrategies.RandomMovement;


public class ZombieToast extends MovingEntity implements EnemyMovement, Enemy, MovementFactor{
    
    public ZombieToast(String type, Location location, double zombieAttack, double zombieHealth) {
        super(type, location, zombieHealth, new BaseAttackStrategy(zombieAttack), new RandomMovement());
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {

        String choice = MovementOptions.encodeLocationsArguments(dungeonMap, this);
        Location next = getMove().MoveOptions(choice).nextLocation(getLocation(),dungeonMap);
        if (!CheckMovementFactor()) {
            return false;
        }
        if (next.equals(getLocation())) {
            return false;
        } else {
            setLocation(next);
            dungeonMap.interactAll(this);

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
