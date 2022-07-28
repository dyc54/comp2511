package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.movementStrategies.MovementStrategy;

public abstract class MovingEntity extends Entity {
    private AttackStrategy attack;
    private MovementStrategy move;
    private double health;
    public MovingEntity(String type, Location location, double health, AttackStrategy attack, MovementStrategy move) {
        super(type, location);
        this.attack = attack;
        this.move = move;
        this.health = health;
    }
    public void setAttack(AttackStrategy attack) {
        this.attack = attack;
    }
    public AttackStrategy getAttack() {
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
