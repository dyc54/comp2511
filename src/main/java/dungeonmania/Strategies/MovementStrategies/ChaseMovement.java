package dungeonmania.Strategies.MovementStrategies;


import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class ChaseMovement implements MovementStrategy{
    private Location location;

    public ChaseMovement(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    private boolean checkMovement(DungeonMap dungeonMap, Location next) {
        return dungeonMap.getEntities(next).stream().anyMatch(entity -> entity.getType().equals("wall") || entity.getType().equals("boulder") || entity.getType().equals("door"));
    }

    @Override
    public Location moveWithWall(Location location, DungeonMap dungeonMap) {
        Location next = new Location();
        if (location.diff(getLocation()).getX() == getLocation().getX()) {
            if (!checkMovement(dungeonMap, getLocation().getLeft()) && !checkMovement(dungeonMap, getLocation().getRight())) {
                return location;
            } else if (!checkMovement(dungeonMap, getLocation().getRight())) {
                next = getLocation().getRight();
            } else {
                next = getLocation().getLeft();
            }
        } else {
            if (!checkMovement(dungeonMap, getLocation().getUp()) && !checkMovement(dungeonMap, getLocation().getDown())) {
                return location;
            } else if (!checkMovement(dungeonMap, getLocation().getDown())) {
                next = getLocation().getDown();
            } else {
                next = getLocation().getUp();
            }
        }
        return next;
    }

    @Override
    public Location nextLocation(Location location) {
        double presentDistance = getLocation().distance(location);
        double distance = presentDistance;
        Location next = new Location();
        if (getLocation().getUp().distance(location) <= presentDistance) {
            next = getLocation().getUp();
            distance = next.distance(location);
        } 
        if (getLocation().getDown().distance(location) <= distance) {
            next = getLocation().getDown();
            distance = next.distance(location);
        }
        if (getLocation().getRight().distance(location) <= distance) {
            next = getLocation().getRight();
            distance = next.distance(location);
        }
        if (getLocation().getLeft().distance(location) <= distance) {
            next = getLocation().getLeft();
            distance = next.distance(location);
        } 
        return next;
    }

    @Override
    public MovementStrategy MoveOptions(String string) {
        // TODO Auto-generated method stub

        return this;
    }
    
}
