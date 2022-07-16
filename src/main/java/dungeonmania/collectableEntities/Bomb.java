package dungeonmania.collectableEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.staticEntities.FloorSwitch;
import dungeonmania.staticEntities.StaticBomb;

public class Bomb extends CollectableEntity implements Useable {

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
        dungeonMap.getPlayer().removeInventoryList(this);
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
    public void use(DungeonMap map, Player player) {
        put(player.getLocation(), map);
        player.getInventory().removeFromInventoryList(this);
    }
}
