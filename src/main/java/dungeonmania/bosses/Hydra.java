package dungeonmania.bosses;

import java.util.Random;

import dungeonmania.Player;
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
    private final double hydraHealthIncreaseRate;
    private final double hydraHealthIncreaseAmount;
    // private final double hydraHealthIncreaseRate;
    // private final double hydraHealthIncreaseRate;
    private double seed;
    public Hydra(String type, Location location, double hydra_health, double hydra_attack, double hydra_health_increase_rate, double hydra_health_increase_amount) {
        super(type, location, hydra_health, 
        new BaseAttackStrategy(hydra_attack)
        // new BaseAttackStrategy(hydra_attack, hydra_health_increase_rate, hydra_health_increase_amount, hydra_health)
        , new RandomMovement());
        seed = hydra_health_increase_rate;
        hydraHealthIncreaseRate = hydra_health_increase_rate;
        hydraHealthIncreaseAmount = hydra_health_increase_amount;
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
    public boolean battleWith(Player player) {
        Random r = new Random(Double.valueOf(seed).hashCode());
        seed = r.nextGaussian();
        if (r.nextDouble() < this.hydraHealthIncreaseRate) {
            return subHealth(-1 * hydraHealthIncreaseAmount);
        } else {
            return subHealth(battleDamageFrom(player));
        }
    }
    
}
