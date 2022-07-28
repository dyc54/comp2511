package dungeonmania.bosses;

import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.strategies.attackStrategies.ChanceAttackStrategy;


public class Hydra extends ZombieToast {

    public Hydra(String type, Location location, double hydra_health, int hydra_attack, double hydra_health_increase_rate, int hydra_health_increase_amount) {
        super(type, location, hydra_attack, hydra_health);
        setAttack(new ChanceAttackStrategy(hydra_attack, hydra_health_increase_rate, hydra_health_increase_amount, hydra_health));
    }
    
}
