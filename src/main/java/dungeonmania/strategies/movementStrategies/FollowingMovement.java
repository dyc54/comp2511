package dungeonmania.strategies.movementStrategies;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class FollowingMovement implements MovementStrategy{
    private final Location playerPrevLocation;
    public FollowingMovement(Location playerPrevLocation) {
        this.playerPrevLocation = playerPrevLocation;
    }
    /**
     * @param location any location does't matter
     */
    @Override
    public Location nextLocation(Location location, DungeonMap dungeonMap) {
        return playerPrevLocation;
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        return this;
    }

    
}
