package dungeonmania.strategies.movementStrategies;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class CircleMovement implements MovementStrategy {
    private Location center;
    private double current;
    private int direction;
    private final double speed = 45;
    public CircleMovement(Location center) {
        System.out.println("Center at" + center.toString());
        this.center = center.clone();
        this.current = 90;
        this.direction = -1;
    }
    /**
     * @param cure any locations
     */
    @Override
    public Location nextLocation(Location curr, DungeonMap dungeonMap) {
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
                this.current -= this.speed * direction;
                direction *= -1;
                break;
            default:
                break;

        }
        return this;
    }


}
