package dungeonmania.MovingEntities;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Strategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Mercenary extends MovingEntity implements MovementStrategy{
    Location location;
    int mercenary_attack;
    int mercenary_health;
    public Mercenary(Location location, int mercenary_attack, int mercenary_health) {
        this.location = location;
        this.mercenary_attack = mercenary_attack;
        this.mercenary_health = mercenary_health;
    }

    @Override
    public void movement(DungeonMap dungeonMap) {
        Collection<Entity> player = dungeonMap.getEntities("player");
        Collection<Entity> walls = dungeonMap.getEntities("Wall");
        Location playerLocation;

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    
}
