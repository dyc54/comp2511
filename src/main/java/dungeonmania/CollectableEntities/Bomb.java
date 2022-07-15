package dungeonmania.CollectableEntities;

import dungeonmania.Accessibility;
import dungeonmania.Entity;
import dungeonmania.StaticEntities.FloorSwitch;
import dungeonmania.StaticEntities.StaticBomb;
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
        StaticBomb staticBomb = new StaticBomb("static_bomb", location.getX(), location.getY(),
                this.bomb_radius);
        dungeonMap.addEntity(staticBomb);
        dungeonMap.getFourNearEntities(location).stream().forEach(e -> {
            if (e instanceof FloorSwitch) {
                FloorSwitch floorSwitch = (FloorSwitch) e;
                if (floorSwitch.getTrigger()) {
                    staticBomb.update(dungeonMap);
                } else {
                    floorSwitch.bombAttach(staticBomb);
                }
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

}
