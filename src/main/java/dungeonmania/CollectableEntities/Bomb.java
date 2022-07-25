package dungeonmania.CollectableEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.StaticEntities.FloorSwitch;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Bomb extends CollectableEntity {

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

    public void put(Location location, DungeonMap dungeonMap) {
        this.setLocation(location);
        this.hasPlaced = true;
        dungeonMap.addEntity(this);
        dungeonMap.getPlayer().removeInventoryList(this);
        dungeonMap.getFourNearEntities(location).stream().forEach(e -> {
            if (e instanceof FloorSwitch) {
                FloorSwitch floorSwitch = (FloorSwitch) e;
                if (floorSwitch.getTrigger()) {
                    this.update(dungeonMap);
                } else {
                    floorSwitch.bombAttach(this);
                }
            }
        });
    }

    public void update(DungeonMap map) {
        map.getEntities(getLocation(), bomb_radius).stream().forEach(e -> {
            if (!(e instanceof Player)) {
                map.removeEntity(e.getEntityId());
            }
        });
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        if (!hasPlaced) {
            super.interact(entity, map);
        }
        return false;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return (!hasPlaced);
    }

}
