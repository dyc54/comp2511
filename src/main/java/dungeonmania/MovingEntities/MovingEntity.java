package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.helpers.Location;

public abstract class MovingEntity extends Entity {
    private AttackStrayegy attack;
    private MovementStrategy move;
    private double health;
    public MovingEntity(String type, Location location, double health, AttackStrayegy attack, MovementStrategy move) {
        super(type, location);
        this.attack = attack;
        this.move = move;
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
    public MovementStrategy getMove() {
        return move;
    }
    public void setMove(MovementStrategy move) {
        this.move = move;
    }
    public MovementStrategy setMove(String options) {
        this.move.MoveOptions(options);
        return move;
    }
}
