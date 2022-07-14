package dungeonmania.Strategies.MovementStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class RandomMovement implements MovementStrategy{
    private String possible;
    public RandomMovement() {
        possible = "udlr";
    }
    @Override
    public Location nextLocation(Location location) {
        // TODO Auto-generated method stub
        
        List<Location> choices = new ArrayList<>(4);
        for (char ch : possible.toCharArray()) {
            switch (ch) {
                case 'u':
                    choices.add(location.getUp());
                    break;
                case 'd':
                    choices.add(location.getDown());
                    break;
                case 'l':
                    choices.add(location.getLeft());
                    System.out.println(location.getLeft().toString());
                    break;
                case 'r':
                    choices.add(location.getRight());
                    break;
                default:
                    break;
            }
        }
        // System.out.println(choices.size() != 0);
        choices.stream().forEach(lo -> System.out.println(lo.toString()));
        if (choices.size() != 0) {
            Random randomchoicer = new Random();
            Location next = choices.get(randomchoicer.nextInt(choices.size()));
                    // System.out.println(next.getLeft().toString());
                    return next;
        }
        return location;   
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        // TODO Auto-generated method stub
        // List<String> 
        // System.out.println(possible);
        // possible = string.replaceAll("[udlr]", "");
        possible = string;
        // System.out.println(possible);
        return this;
    }

    @Override
    public Location moveWithWall(Location location, DungeonMap dungeonMap) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
