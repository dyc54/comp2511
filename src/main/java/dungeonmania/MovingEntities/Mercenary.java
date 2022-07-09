package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.Strategies.EnemyMovementStrategy;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Mercenary extends MovingEntity implements EnemyMovementStrategy {
    Location location;
    int mercenary_attack;
    int mercenary_health;
    public Mercenary(String type, Location location, int mercenary_attack, int mercenary_health) {
        this.location = location;
        this.mercenary_attack = mercenary_attack;
        this.mercenary_health = mercenary_health;
        setType(type);
    }

    @Override
    public void movement(DungeonMap dungeonMap) {
        Collection<Entity> player = dungeonMap.getEntities("player");
        Collection<Entity> walls = dungeonMap.getEntities("wall");
        Collection<Entity> doors = dungeonMap.getEntities("door");
        Location playerLocation = new Location();
        List<Location> wallAndDoorList = new ArrayList<>();

        for (Entity entity: player) {
            playerLocation = entity.getLocation();
        }
        walls.stream().forEach(wall -> wallAndDoorList.add(wall.getLocation()));
        doors.stream().forEach(door-> wallAndDoorList.add(door.getLocation()));
        double presentDistance = playerLocation.distance(getLocation());
        boolean hasMove = false;
        if (Location.getUp(location).distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(Location.getUp(location))) {
                setLocation(Location.getUp(location));
                hasMove = true;
            }
        }else if (!hasMove && Location.getDown(location).distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(Location.getDown(location))) {
                setLocation(Location.getDown(location));
                hasMove = true;
            }
        } else if (!hasMove && Location.getLeft(location).distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(Location.getLeft(location))) {
                setLocation(Location.getLeft(location));
                hasMove = true;
            }
        } else if (!hasMove && Location.getRight(location).distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(Location.getRight(location))) {
                setLocation(Location.getRight(location));
                hasMove = true;
            }
        }
    
        if (!hasMove && !wallAndDoorList.contains(Location.getRight(location))) {
            setLocation(Location.getRight(location));
        } 
        if (!hasMove && !wallAndDoorList.contains(Location.getRight(location))) {
            setLocation(Location.getRight(location));
        }
        if (!hasMove && !wallAndDoorList.contains(Location.getRight(location))) {
            setLocation(Location.getRight(location));
        }
        if (!hasMove && !wallAndDoorList.contains(Location.getRight(location))) {
            setLocation(Location.getRight(location));
        }
        dungeonMap.UpdateEntity(this);
    }

    public void followPlayer(Entity player) {
        Location location = ((Player) player).getPreviousLocation();
        setLocation(location);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
