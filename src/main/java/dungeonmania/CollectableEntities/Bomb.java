package dungeonmania.CollectableEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Bomb extends CollectableEntity{

    private int bomb_radius;
    private boolean hasPlaced;
    public Bomb(String type, int x, int y, int bomb_radius) {
        super(type, x, y);
        this.bomb_radius = bomb_radius;
        hasPlaced = false;
    }

    public int getBomb_radius() {
        return bomb_radius;
    }
    public void put(Location location) {
        setLocation(location);
    }
    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // TODO Auto-generated method stub
        if (!hasPlaced) {
            super.interact(entity, map);
        } 
        return false;
    }
}
