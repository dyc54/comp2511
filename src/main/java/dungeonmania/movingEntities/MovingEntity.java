package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.MovementFactor;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.movementStrategies.MovementStrategy;

public abstract class MovingEntity extends Entity implements MovementFactor{
    private AttackStrategy attack;
    private MovementStrategy move;
    private double health;
    private int movementFactor;

    public MovingEntity(String type, Location location, double health, AttackStrategy attack, MovementStrategy move) {
        super(type, location);
        this.attack = attack;
        this.move = move;
        this.health = health;
        this.movementFactor = 0;
    }
    public AttackStrategy getAttack() {
        return attack;
    }
    public int getMovementFactor() {
        return this.movementFactor;
    }
    public void setMovementFactor(int movementFactor) {
        this.movementFactor = movementFactor;
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

    @Override
    public void resetMovementFactor(int movementFactor) {
        // TODO Auto-generated method stub
        this.movementFactor = movementFactor;
    }

    @Override
    public boolean CheckMovementFactor() {
        System.out.println("----------------movementfactor zombie--------------");
        System.out.println(this.movementFactor);
        if (this.movementFactor > 0) {
            this.movementFactor -= 1;
            return false;
        }
        return true;
    }

}
