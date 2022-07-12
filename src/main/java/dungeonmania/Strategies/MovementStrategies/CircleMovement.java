package dungeonmania.Strategies.MovementStrategies;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class CircleMovement implements MovementStrategy {
    private Location center;
    private double current;
    private int direction;
    private final double speed = 45;
    public CircleMovement(Location center, boolean isClockWise) {
        this.center = center;
        this.current = 90;
        if (isClockWise) {
            this.direction = -1;
        } else {
            this.direction = 1;
        }
    }

    @Override
    public Location nextLocation(Location curr) {
        if (curr.equals(center)) {
            return center.getUp();
        }
        return center.getLocation(this.current + this.speed * direction, 1);
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        switch (string) {
            case "CHANGE_DIRECTION": 
                direction *= -1;
                break;
            default:
                break;

        }
        return this;
    }

}
