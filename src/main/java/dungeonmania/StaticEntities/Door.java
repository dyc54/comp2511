package dungeonmania.StaticEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.CollectableEntities.Key;
import dungeonmania.MovingEntities.Spider;
import dungeonmania.helpers.DungeonMap;

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
        // TODO Auto-generated method stub
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
            Boolean canOpen = player.getInventoryList().stream().anyMatch(e -> {
                if (e instanceof Key) {
                    int keyKey = ((Key) e).getKey();
                    if (keyKey == key) {
                        open();
                        player.getInventory().removeFromInventoryList(e);
                        return true;
                    } 
                    return false;
                }
                return false;
            });
            if (canOpen && getLocation().equals(entity.getLocation()) && DungeonMap.isaccessible(map, getLocation(), entity)) {
                // System.out.println(String.format("Door: get back %s ->  %s", player.getLocation().toString(), player.getPreviousLocation().toString()));
                // player.setLocation(player.getPreviousLocation());
                player.setLocation(getLocation());
            }
        }
        return false;
    }
    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap  map) {
        // do nothing by defalut 
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }
}
