package dungeonmania.Strategies.MovementStrategies;


import java.util.ArrayList;
import java.util.TreeMap;

import dungeonmania.helpers.Location;

public class ChaseMovement implements MovementStrategy{
    private Location location;
    private String possible;
    public ChaseMovement(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public Location nextLocation(Location location) {
        Location next = getLocation();
        TreeMap<Integer, Location> ordered = new TreeMap<>();

        ArrayList<Location> choices = new ArrayList<>(4);
        for (char ch : possible.toCharArray()) {
            switch (ch) {
                case 'u':
                    choices.add(this.location.getUp());
                    break;
                case 'd':
                    choices.add(this.location.getDown());
                    break;
                case 'l':
                    choices.add(this.location.getLeft());
                    System.out.println(this.location.getLeft().toString());
                    break;
                case 'r':
                    choices.add(this.location.getRight());
                    break;
                default:
                    break;
            }
        }
        System.out.println(location);
        choices.stream().forEach(ele -> {
            int distance = location.distance(ele);
            ordered.put(Integer.valueOf(distance), ele);
        });
        
        for (Integer cloestDistance : ordered.keySet()) {
            Location temp = ordered.get(cloestDistance);
            if (choices.contains(temp)) {
                next = temp;
                break;
            }
        }
        return next;
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        possible = string;
        return this;
    }
    
}
