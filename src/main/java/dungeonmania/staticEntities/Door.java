package dungeonmania.staticEntities;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.Openable;
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

    public boolean isOpened() {
        return opened;
    }

    public void close() {
        opened = false;
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

    public int getKey() {
        return this.key;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // DO nothing by defalut
        if (entity instanceof Player && !opened) {
            Player player = (Player) entity;
                Boolean canOpen = player.getInventoryList().stream().anyMatch(e -> {
                if (e instanceof Openable) {
                    Openable openEntity = (Openable) e;
                    return openEntity.open(this, player);
                }
                return false;
            });
            // If the door can be opened, move the player
            if (canOpen && DungeonMap.isaccessible(map, getLocation(), entity)) {
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
    
    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject().put("key", key);
    }
}
