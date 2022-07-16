package dungeonmania.Strategies.MovementStrategies;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import dungeonmania.helpers.DungeonMap;
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
        // double distance = getLocation().distance(location);
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
            // System.out.println(distance);
            ordered.put(Integer.valueOf(distance), ele);
        });
        // Iterator<Integer> cloestDistance = ordered.descendingKeySet().descendingIterator();
        
        
        for (Integer cloestDistance : ordered.keySet()) {
            Location temp = ordered.get(cloestDistance);
            if (choices.contains(temp)) {
                // System.out.println(cloestDistance);
                next = temp;
                break;
            }
        }
        // for (Location choice : choices) {
        //     if (ordered.get(cloestDistance.next()))
        // }
        // if (getLocation().getUp().distance(location) <= distance) {
        //     next = getLocation().getUp();
        //     distance = next.distance(location);
        // } 
        // if (getLocation().getDown().distance(location) <= distance) {
        //     next = getLocation().getDown();
        //     distance = next.distance(location);
        // }
        // if (getLocation().getRight().distance(location) <= distance) {
        //     next = getLocation().getRight();
        //     distance = next.distance(location);
        // }
        // if (getLocation().getLeft().distance(location) <= distance) {
        //     next = getLocation().getLeft();
        //     distance = next.distance(location);
        // } 
        return next;
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        // TODO Auto-generated method stub
        possible = string;
        return this;
    }
    
}
