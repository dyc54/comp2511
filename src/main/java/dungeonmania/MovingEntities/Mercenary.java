package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.Strategies.EnemyMovementStrategy;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Mercenary extends MovingEntity implements EnemyMovementStrategy {
    // Location location;
    double mercenary_attack;
    double mercenary_health;
    public Mercenary(String type, Location location, double mercenary_attack, double mercenary_health) {
        super(type, location, mercenary_health, new BaseAttackStrategy(mercenary_attack));
        // this.location = location;
        this.mercenary_attack = mercenary_attack;
        this.mercenary_health = mercenary_health;
        // setType(type);
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
        if (getLocation().getUp().distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(getLocation().getUp())) {
                setLocation(getLocation().getUp());
                hasMove = true;
            }
        }else if (!hasMove && getLocation().getDown().distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(getLocation().getDown())) {
                setLocation(getLocation().getDown());
                hasMove = true;
            }
        } else if (!hasMove && getLocation().getLeft().distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(getLocation().getLeft())) {
                setLocation(getLocation().getLeft());
                hasMove = true;
            }
        } else if (!hasMove && getLocation().getRight().distance(playerLocation) <= presentDistance) {
            if (!wallAndDoorList.contains(getLocation().getRight())) {
                setLocation(getLocation().getRight());
                hasMove = true;
            }
        }
    
        if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
            setLocation(getLocation().getRight());
        } 
        if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
            setLocation(getLocation().getRight());
        }
        if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
            setLocation(getLocation().getRight());
        }
        if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
            setLocation(getLocation().getRight());
        }
        dungeonMap.UpdateEntity(this);
    }

    public void followPlayer(Entity player) {
        Location location = ((Player) player).getPreviousLocation();
        setLocation(location);
    }

    // public Location getLocation() {
    //     return location;
    // }

    // public void setLocation(Location location) {
    //     this.location = location;
    // }

}
