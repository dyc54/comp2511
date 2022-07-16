package dungeonmania.StaticEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;

public class StaticBomb extends StaticEntity {

    private int bomb_radius;
    protected FloorSwitch Switch;

    public StaticBomb(String type, int x, int y, int bomb_radius) {
        super(type, x, y);
        this.bomb_radius = bomb_radius;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }

    public void update(DungeonMap map) {
        map.getEntities(getLocation(), bomb_radius).stream().forEach(e -> {
            if (!(e instanceof Player)) {
                map.removeEntity(e.getEntityId());
            }
        });
    }
}
