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
        // TreeMap<Integer, Location> ordered = new TreeMap<>();
        // List<Location> choices = MovementOptions.decodeLocationsArguments(this.location, possible);
        // choices.stream().forEach(choice -> System.out.println(choice));
        // choices.stream().forEach(ele -> {
        //     int distance = location.distance(ele);
        //     System.out.println("DISTANCE: ++++++"+distance);
        //     ordered.put(Integer.valueOf(distance), ele);
        // });
        // System.out.println(ordered.keySet()+"**************");
        // for (Integer cloestDistance : ordered.keySet()) {
        //     Location temp = ordered.get(cloestDistance);
        //     if (choices.contains(temp)) {
        //         next = temp;
        //         break;
        //     }
        // }
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
