package dungeonmania.Strategies.MovementStrategies;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class FollowingMovement implements MovementStrategy{

    @Override
    public Location nextLocation(Location location) {
        return location;
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Location moveWithWall(Location location, DungeonMap dungeonMap) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
