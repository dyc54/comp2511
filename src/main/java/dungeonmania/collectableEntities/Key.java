package dungeonmania.collectableEntities;

import org.json.JSONObject;

import dungeonmania.Player;
import dungeonmania.staticEntities.Door;

public class Key extends CollectableEntity  implements ItemInventoryLimit, openable{
    private final int key;
    public Key(String type, int x, int y, int key) {
        super(type, x, y);
        this.key = key;
    }
    public int getKey() {
        return key;
    }
    @Override
    public int getMax() {
        return 1;
    }
    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject().put("key", key);
    }
    @Override
    public boolean open(Door door, Player player) {
        // Check if there are sunstone in player's bag
        // Use the sunstone to open the door
        if (player.getInventoryList().stream().anyMatch(e -> e instanceof SunStone)) {
            return false;
        }
        // Check if the key is for this door
        int keyKey = this.getKey();
        if (keyKey == door.getKey()) {
            door.open();
            player.getInventory().removeFromInventoryList(this);
            return true;
        }
        return false;
    }
}
