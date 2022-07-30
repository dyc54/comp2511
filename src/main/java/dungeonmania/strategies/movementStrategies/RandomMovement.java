package dungeonmania.strategies.movementStrategies;

import java.util.List;
import java.util.Random;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class RandomMovement implements MovementStrategy{
    private String possible;
    public RandomMovement() {
        possible = "udlr";
    }
    
    /**
     * @param location current entity's location
     */
    @Override
    public Location nextLocation(Location location, DungeonMap dungeonMap) {
        
        List<Location> choices = MovementOptions.decodeLocationsArguments(location, possible);
        if (choices.size() != 0) {
            Random randomchoicer = new Random(location.hashCode());
            Location next = choices.get(randomchoicer.nextInt(choices.size()));
            return next;
        }
        return location;   
    }

    /**
     * Passed in the location arguments which have the locations can be moved to.
     */
    @Override
    public MovementStrategy MoveOptions(String string) {
        possible = string;
        return this;
    }

}
