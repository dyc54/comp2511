package dungeonmania.strategies.movementStrategies;


import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import dungeonmania.helpers.DijstraAlgorithm;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class ChaseMovement implements MovementStrategy{
    private Location location;
    private String possible;
    /**
     * @param location entity's location
     */
    public ChaseMovement(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
    /**
     * @param location Player's location
     */
    @Override
    public Location nextLocation(Location location, DungeonMap dungeonMap) {
        
        Location next = getLocation();
        if (location.equals(getLocation())) {
            return next;
        }
        DijstraAlgorithm dijstraAlgorithm = new DijstraAlgorithm(location, dungeonMap, getLocation());
        next = dijstraAlgorithm.dijstra();
        return next;
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
