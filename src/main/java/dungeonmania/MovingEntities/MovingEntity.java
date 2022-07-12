package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.helpers.Location;

public abstract class MovingEntity extends Entity {
    private AttackStrayegy attack;
    private double health;
    public MovingEntity(String type, Location location, double health, AttackStrayegy attack) {
        super(type, location);
        this.attack = attack;
        this.health = health;
    }
    public AttackStrayegy getAttack() {
        return attack;
    }
    public double getHealth() {
        return health;
    }
    public void setHealth(double health) {
        this.health = health;
    }
}
