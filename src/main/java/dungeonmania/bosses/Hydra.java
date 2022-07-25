package dungeonmania.bosses;

import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.MovingEntity;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class Hydra extends MovingEntity {

    public Hydra(String type, Location location, double health, int attack) {
        super(type, location, health, new BaseAttackStrategy(attack), new RandomMovement());
    }
    
}
