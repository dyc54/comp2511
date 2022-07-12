package dungeonmania.Strategies.MovementStrategies;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class CircleMovement implements MovementStrategy {
    private Location center;
    private double current;
    private int direction;
    private final double speed = 45;
    public CircleMovement(Location center, boolean isClockWise) {
        System.out.println("Center at" + center.toString());
        this.center = center.clone();
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
        this.current += this.speed * direction;
        return center.getLocation(this.current, 1);
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        switch (string) {
            case "CHANGE_DIRECTION": 
                // System.out.println("reverse");
                this.current -= this.speed * direction;
                direction *= -1;
                break;
            default:
                break;

        }
        return this;
    }

}
