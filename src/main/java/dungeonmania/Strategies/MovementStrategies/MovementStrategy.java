package dungeonmania.Strategies.MovementStrategies;

import dungeonmania.helpers.Location;

public interface MovementStrategy {
    public Location nextLocation(Location location);
    public MovementStrategy MoveOptions(String string);
}
