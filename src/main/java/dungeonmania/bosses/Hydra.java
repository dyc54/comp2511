package dungeonmania.bosses;

import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.MovingEntity;
import dungeonmania.strategies.EnemyMovement;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.attackStrategies.ChanceAttackStrategy;
import dungeonmania.strategies.movementStrategies.MovementOptions;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class Hydra extends MovingEntity implements EnemyMovement, Enemy {

    public Hydra(String type, Location location, double hydra_health, int hydra_attack, double hydra_health_increase_rate, int hydra_health_increase_amount) {
        super(type, location, hydra_health, new ChanceAttackStrategy(hydra_attack, hydra_health_increase_rate, hydra_health_increase_amount, hydra_health), new RandomMovement());
        
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
    public String getEnemyType() {
        return getType();
    }

    @Override
    public double getHealth() {
        return super.getHealth();
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        String choice = MovementOptions.encodeLocationsArguments(dungeonMap, this);
        Location next = getMove().MoveOptions(choice).nextLocation(getLocation(), dungeonMap);
        if (next.equals(getLocation())) {
            return false;
        } else {
            setLocation(next);
            dungeonMap.UpdateEntity(this);
            return true;
        }
    }
    
}
