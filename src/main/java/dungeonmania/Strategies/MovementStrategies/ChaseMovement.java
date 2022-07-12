package dungeonmania.Strategies.MovementStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dungeonmania.helpers.Location;

public class ChaseMovement implements MovementStrategy{
    public Location location;
    public boolean following;
    public ChaseMovement(Location location) {
        this.location = location;
    }
    @Override
    public Location nextLocation(Location location) {
        // TODO Auto-generated method stub
        
        // Collection<Entity> entities = new LinkedList<>();
        // location.getEightNearPosition().stream().forEach(position -> {
        //     if (map.containsKey(position.apply(location))) {
        //         entities.addAll(map.get(position.apply(location)));
        //     }
        // });

        // location.getFourNearPosition().stream().forEach(position -> {

        // });
        List<Location> choices = new ArrayList<>(4);
        Location diff = this.location.diff(location);
        if (diff.getX() > 0) {
            choices.add(location.getRight());
        } else {
            choices.add(location.getLeft());
        }
        if (diff.getY() > 0) {
            choices.add(location.getUp());
        } else {
            choices.add(location.getDown());
        }
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

        return this;
    }
    
}
