package dungeonmania.collectableEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.staticEntities.FloorSwitch;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Bomb extends CollectableEntity implements Useable{

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

    @Override
    public void use(DungeonMap map, Player player) {
        this.setLocation(player.getLocation());
        this.hasPlaced = true;
        map.addEntity(this);
        map.getPlayer().removeInventoryList(this);
        map.getFourNearEntities(player.getLocation()).stream().forEach(e -> {
            if (e instanceof FloorSwitch) {
                FloorSwitch floorSwitch = (FloorSwitch) e;
                if (floorSwitch.getTrigger()) {
                    this.update(map);
                } else {
                    floorSwitch.bombAttach(this);
                }
            }
        });
        
    }

}
