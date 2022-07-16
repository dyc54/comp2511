package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.Key;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.movingEntities.Spider;

public class Door extends StaticEntity {
    private final int key;
    private boolean opened;

    public Door(String type, int x, int y, int key) {
        super(type, x, y);
        this.key = key;
        opened = false;
    }

    public int getKey() {
        return key;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        if (entity instanceof Spider) {
            return true;
        }
        return opened;
    }

    public void open() {
        opened = true;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // DO nothing by defalut
        if (entity instanceof Player && !opened) {
            Player player = (Player) entity;
            // If the player has the correct key, open the door
            Boolean canOpen = player.getInventoryList().stream().anyMatch(e -> {
                if (e instanceof Key) {
                    // Check if the key is for this door
                    int keyKey = ((Key) e).getKey();
                    if (keyKey == key) {
                        open();
                        player.getInventory().removeFromInventoryList(e);
                        return true;
                    }
                }
                return false;
            });
            // If the door can be opened, move the player
            if (canOpen && getLocation().equals(entity.getLocation())
                    && DungeonMap.isaccessible(map, getLocation(), entity)) {
                player.setLocation(getLocation());
            }
        }
        return false;
    }

    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap map) {
        // do nothing by defalut
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }
}
