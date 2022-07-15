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

    @Override
    public Location moveWithWall(Location location, DungeonMap dungeonMap) {
        Location next = new Location();
        Location diff = getLocation().diff(location);
        if (diff.getX() == 0) {
            if (dungeonMap.checkMovement(getLocation().getRight()) && dungeonMap.checkMovement(getLocation().getRight())) {
                next = getLocation().changeLocation(location);
            } else if (!(dungeonMap.checkMovement(getLocation().getRight()) || dungeonMap.checkMovement(getLocation().getRight()))) {
                next = getLocation().getRight().distance(dungeonMap.getPlayer().getLocation()) > getLocation().getLeft().distance(dungeonMap.getPlayer().getLocation())? getLocation().getLeft(): getLocation().getRight();
            } else if (!dungeonMap.checkMovement(getLocation().getRight())) {
                next = getLocation().getRight();
            } else {
                next = getLocation().getLeft();
            }
        } else {
            if (dungeonMap.checkMovement(getLocation().getUp()) && dungeonMap.checkMovement(getLocation().getDown())) {
                next = getLocation().changeLocation(location);
            }else if (!(dungeonMap.checkMovement(getLocation().getUp()) || dungeonMap.checkMovement(getLocation().getDown()))) {
                System.out.println("-------------------");
                next = getLocation().getUp().distance(dungeonMap.getPlayer().getLocation()) > getLocation().getDown().distance(dungeonMap.getPlayer().getLocation())? getLocation().getDown(): getLocation().getUp();
            } else if (!dungeonMap.checkMovement(getLocation().getDown())) {
                next = getLocation().getDown();
            } else {
                next = getLocation().getUp();
            }
        }
        return next;
    }

    @Override
    public Location nextLocation(Location location) {
        double distance = getLocation().distance(location);
        Location next = new Location();
        if (getLocation().getUp().distance(location) <= distance) {
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
