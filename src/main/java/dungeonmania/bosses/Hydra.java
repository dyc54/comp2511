package dungeonmania.bosses;

import java.util.Random;

import dungeonmania.Player;
import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;


public class Hydra extends ZombieToast {
    
    private final double hydraHealthIncreaseRate;
    private final int hydraHealthIncreaseAmount;
    private double seed;

    public Hydra(String type, Location location, double hydra_health, int hydra_attack, double hydra_health_increase_rate, int hydra_health_increase_amount) {
        super(type, location, hydra_attack, hydra_health);
        // setAttack(new BaseAttackStrategy(hydra_attack, hydra_health_increase_rate, hydra_health_increase_amount, hydra_health));
        hydraHealthIncreaseRate = hydra_health_increase_rate;
        hydraHealthIncreaseAmount = hydra_health_increase_amount;
    }
    
    @Override
    public boolean battleWith(Player player) {
        Random r = new Random(Double.valueOf(seed).hashCode());
        seed = r.nextGaussian();
        double rate = r.nextDouble();
        if (rate < this.hydraHealthIncreaseRate) {
            return subHealth(-1 * hydraHealthIncreaseAmount);
        } else {
            return subHealth(battleDamageFrom(player));
        }
    }
    
}
