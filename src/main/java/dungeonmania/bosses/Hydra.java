package dungeonmania.bosses;

import java.util.Random;

import dungeonmania.Player;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.ZombieToast;


public class Hydra extends ZombieToast {
    
    private final double hydraHealthIncreaseRate;
    private final int hydraHealthIncreaseAmount;
    private double seed;

    public Hydra(String type, Location location, double hydraHealth, int hydraAttack, double hydraHealthIncreaseRate, int hydraHealthIncreaseAmount) {
        super(type, location, hydraAttack, hydraHealth);
        this.hydraHealthIncreaseRate = hydraHealthIncreaseRate;
        this.hydraHealthIncreaseAmount = hydraHealthIncreaseAmount;
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
