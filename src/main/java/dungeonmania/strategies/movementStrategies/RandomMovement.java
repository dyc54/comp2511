package dungeonmania.strategies.movementStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        choices.stream().forEach(lo -> System.out.println(lo.toString()));
        if (choices.size() != 0) {
            Random randomchoicer = new Random();
            Location next = choices.get(randomchoicer.nextInt(choices.size()));
            return next;
        }
        return location;   
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        // TODO Auto-generated method stub
        possible = string;
        return this;
    }

}
