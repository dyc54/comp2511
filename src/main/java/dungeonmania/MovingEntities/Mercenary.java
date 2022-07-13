package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Mercenary extends MovingEntity implements EnemyMovement, Interact, Enemy {
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

    private boolean checkMovement(DungeonMap dungeonMap, Location next) {
        return dungeonMap.getEntities(next).stream().anyMatch(entity -> entity.getType().equals("wall") || entity.getType().equals("boulder") || entity.getType().equals("door"));
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        MovementStrategy strategy = super.getMove();
        Location playerLocation = new Location();
        Collection<Entity> player = dungeonMap.getEntities("player");
        for (Entity entity: player) {
            Player p = (Player) entity;
            playerLocation = p.getLocation();
        }
        Location next = strategy.nextLocation(playerLocation);
        if (!checkMovement(dungeonMap, next)) {
            setLocation(next);
        } else {
            next = strategy.moveWithWall(next, dungeonMap);
            if (next.equals(getLocation())) {
                return false;
            } else {
                setLocation(next);
            }
        }
        dungeonMap.UpdateEntity(this);
        return true;

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

    @Override
    public AttackStrayegy getAttackStrayegy() {
        // TODO Auto-generated method stub
        return getAttack();
    }

    @Override
    public String getEnemyId() {
        // TODO Auto-generated method stub
        return super.getEntityId();
    }

    @Override
    public String getEnemyType() {
        // TODO Auto-generated method stub
        return getType();
    }

    @Override
    public double getHealth() {
        return super.getHealth();
    }
    
    // public Location getLocation() {
    //     return location;
    // }

    // public void setLocation(Location location) {
    //     this.location = location;
    // }

}
