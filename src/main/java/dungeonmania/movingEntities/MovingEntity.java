package dungeonmania.movingEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.MovementFactor;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.battleStrategies.BattleStrategyWithPlayer;
import dungeonmania.strategies.movementStrategies.MovementStrategy;

public abstract class MovingEntity extends Entity implements BattleStrategyWithPlayer,MovementFactor {
// public abstract class MovingEntity extends Entity implements MovementFactor{
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
    public void setAttack(AttackStrategy attack) {
        this.attack = attack;
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
    @Override
    public boolean subHealth(double damage) {
        this.health = health - damage;
        if (health <= 0.0002) {
            health = 0.0;
            return false;
        }
        return true;
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
    public boolean battleWith(Player player) {
        return subHealth(battleDamageFrom(player));
    }
    @Override
    public double battleDamageFrom(Player player) {
        AttackStrategy attackStrayegy = player.getAttackStrategy();
        return attackStrayegy.attackDamage() / 5.0;
    }
    @Override
    public boolean isAlive() {
        return getHealth() > 0.0;
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
