package dungeonmania.inventories;

import java.util.HashMap;

public class InventoryViewer {
    private HashMap<String, Integer> inventory;
    public InventoryViewer(Inventory parent) {
        inventory = new HashMap<>();
        parent.getInventory().keySet().stream().forEach(key -> {
            this.inventory.put(key, parent.getInventory().get(key).size());
        });
    }
    public boolean removeFromInventoryList(String type, int number) {
        if (!inventory.containsKey(type)) {
            return false;
        }
        int currAmount = inventory.get(type).intValue() ;
        if (currAmount < number) {
            return false;
        } else {
            inventory.replace(type, Integer.valueOf(currAmount - number));
        }
        return true;
    }
    public int countItem(String type) {
        if (inventory.containsKey(type)) {
            return inventory.get(type).intValue();
        }
        return 0;
    }
}
