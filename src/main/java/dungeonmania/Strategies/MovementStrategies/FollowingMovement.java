package dungeonmania.Strategies.MovementStrategies;

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
    
}
