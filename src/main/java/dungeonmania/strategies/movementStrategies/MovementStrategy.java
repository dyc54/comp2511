package dungeonmania.strategies.movementStrategies;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public interface MovementStrategy {
    public Location nextLocation(Location location, DungeonMap dungeonMap);
    public MovementStrategy MoveOptions(String string);
}
