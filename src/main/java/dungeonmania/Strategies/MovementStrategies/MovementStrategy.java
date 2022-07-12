package dungeonmania.Strategies.MovementStrategies;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public interface MovementStrategy {
    public Location nextLocation(Location location);
    // public Location alternativeLocation(DungeonMap dungeonMap);
    public MovementStrategy MoveOptions(String string);
}
