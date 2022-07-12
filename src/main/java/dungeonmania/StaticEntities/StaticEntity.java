package dungeonmania.StaticEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.Location;

public abstract class StaticEntity extends Entity{
    public StaticEntity(String type, int x, int y) {
        super(type, x, y);
    }
    public StaticEntity(String type, Location location) {
        super(type, location);        
    }
}
