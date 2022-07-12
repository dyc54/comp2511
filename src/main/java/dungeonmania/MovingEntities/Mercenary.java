package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Mercenary extends MovingEntity implements EnemyMovement, Interact {
    // Location location;
    double mercenary_attack;
    double mercenary_health;
    int bribe_amount;
    int bribe_radius;
    public Mercenary(String type, Location location, double mercenary_attack, double mercenary_health, int bribe_amount, int bribe_radius) {
        super(type, location, mercenary_health, new BaseAttackStrategy(mercenary_attack), new ChaseMovement(location));
        this.mercenary_attack = mercenary_attack;
        this.mercenary_health = mercenary_health;
        this.bribe_amount = bribe_amount;
        this.bribe_radius = bribe_radius;
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        
        return true;
        // Collection<Entity> walls = dungeonMap.getEntities("wall");
        // Collection<Entity> doors = dungeonMap.getEntities("door");
        // List<Location> wallAndDoorList = new ArrayList<>();
        // walls.stream().forEach(wall -> wallAndDoorList.add(wall.getLocation()));
        // doors.stream().forEach(door-> wallAndDoorList.add(door.getLocation()));
        // double presentDistance = playerLocation.distance(getLocation());
        // boolean hasMove = false;
        // if (getLocation().getUp().distance(playerLocation) <= presentDistance) {
        //     if (!wallAndDoorList.contains(getLocation().getUp())) {
        //         setLocation(getLocation().getUp());
        //         hasMove = true;
        //     }
        // }else if (!hasMove && getLocation().getDown().distance(playerLocation) <= presentDistance) {
        //     if (!wallAndDoorList.contains(getLocation().getDown())) {
        //         setLocation(getLocation().getDown());
        //         hasMove = true;
        //     }
        // } else if (!hasMove && getLocation().getLeft().distance(playerLocation) <= presentDistance) {
        //     if (!wallAndDoorList.contains(getLocation().getLeft())) {
        //         setLocation(getLocation().getLeft());
        //         hasMove = true;
        //     }
        // } else if (!hasMove && getLocation().getRight().distance(playerLocation) <= presentDistance) {
        //     if (!wallAndDoorList.contains(getLocation().getRight())) {
        //         setLocation(getLocation().getRight());
        //         hasMove = true;
        //     }
        // }
    
        // if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
        //     setLocation(getLocation().getRight());
        // } 
        // if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
        //     setLocation(getLocation().getRight());
        // }
        // if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
        //     setLocation(getLocation().getRight());
        // }
        // if (!hasMove && !wallAndDoorList.contains(getLocation().getRight())) {
        //     setLocation(getLocation().getRight());
        // }
        // dungeonMap.UpdateEntity(this);
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        Collection<Entity> entities = dungeonMap.getEntities(player.getLocation(), this.bribe_radius);
        if (entities.stream().anyMatch(entity -> entity.getType().equals("mercenary"))) {
             
            if (player.getInventory().removeFromInventoryList("treasure", this.bribe_amount)) {
                setType("ally");
                dungeonMap.UpdateEntity(this);
                return true;
            }
            return false;
        }
        return false;
    }

    // public Location getLocation() {
    //     return location;
    // }

    // public void setLocation(Location location) {
    //     this.location = location;
    // }

}
