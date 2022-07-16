package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Strategies.AttackStrategies.AttackStrategy;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

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
    public static String getPossibleNextDirection(DungeonMap map, MovingEntity entity) {
        String possible = "";

        possible += DungeonMap.isaccessible(map, entity.getLocation().getUp(), entity) ? "u": "";
        possible += DungeonMap.isaccessible(map, entity.getLocation().getDown(), entity) ? "d": "";
        possible += DungeonMap.isaccessible(map, entity.getLocation().getLeft(), entity) ? "l": "";
        possible += DungeonMap.isaccessible(map, entity.getLocation().getRight(), entity) ? "r": "";
        return possible;
    }

}