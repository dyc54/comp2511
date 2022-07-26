package dungeonmania.inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.*;
import dungeonmania.helpers.IdCollection;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.attackStrategies.BonusDamageMul;
import dungeonmania.strategies.attackStrategies.BonusDamageStrategy;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class Inventory {
    private final HashMap<String, List<Entity>> inventory = new HashMap<>();
    private final IdCollection<String> idCollection = new IdCollection<>();

    /**
     * add Entity to InventoryList, key is Entity type
     * 
     * @param Entity item
     */
    public boolean addToInventoryList(Entity item, Player player) {
        if (item instanceof ItemInventoryLimit && countItem(item.getType()) >= ((ItemInventoryLimit) item).getMax()) {
            return false;
        } 
        if (!idCollection.hasId(item.getEntityId())) {
            idCollection.put(item.getEntityId(), item.getType());   
        }
        if(inventory.containsKey(item.getType())){
            inventory.get(item.getType()).add(item);
        } else {
            List<Entity> itemList = new ArrayList<>();
            itemList.add(item);
            inventory.put(item.getType(), itemList);
        }
        System.out.println(String.format("Inventory: %s has been picked up", item.getType()));
        if (item instanceof BonusDamageAdd) {
            player.getAttackStrategy().bonusDamage((BonusDamageAdd) item);
            System.out.println(String.format("%s is used as weapon", item.getEntityId()));
        } else if (item instanceof BonusDamageMul) {
            player.getAttackStrategy().bonusDamage((BonusDamageMul) item);
            System.out.println(String.format("%s is used as weapon", item.getEntityId()));
        } else if (item instanceof BonusDefenceAdd) {
            player.getDefenceStrayegy().bonusDefence((BonusDefenceAdd) item);
            System.out.println(String.format("%s is used as weapon", item.getEntityId()));
        }
        return true;
    }

    /**
     * remove Entity from InventoryList by Entity
     * 
     * @param Entity item
     */
    public void removeFromInventoryList(Entity item) {
        inventory.get(item.getType()).remove(item);
        idCollection.remove(item.getEntityId());
    }
    public boolean removeFromInventoryList(String type, int number) {
        if (!inventory.containsKey(type)) {
            String.format("%s does not exit", type);
            return false;
        }
        List<Entity> items = inventory.get(type);
        if (items.size() < number) {
            String.format("%s does not have enough amount %d/%d",type, number, countItem(type));
            return false;
        } else {
            for(int i = 0; i < number; i++) {
                idCollection.remove(items.get(0).getEntityId());
                items.remove(0);
            }
        }
        System.out.println(String.format("%s has remove %d from inventory", type, number));
        return true;
    }
    public Entity getItem(String id) {
        
        for (Entity entity: getAllInventory()) {
            if (entity.getEntityId().equals(id)) {
                return entity;
            }
        }
        return null;
    }
    /**
     * remove Entity from InventoryList by Entity id
     * 
     * @param itemId 
     */
    public boolean removeFromInventoryList(String itemId, Player player) {
        if (!idCollection.hasId(itemId)) {
            return false;
        }
        String type = idCollection.get(itemId);
        Entity item = getItem(itemId);
        if (item != null) {
            if (item instanceof BonusDamageAdd) {
                player.getAttackStrategy().removeBounus((BonusDamageAdd) item);
            } else if (item instanceof BonusDamageMul) {
                player.getAttackStrategy().removeBounus((BonusDamageMul) item);
            } else if (item instanceof BonusDefenceAdd) {
                player.getDefenceStrayegy().removeDefence((BonusDefenceAdd) item);
            }
            inventory.get(type).remove(item);
            System.out.println(String.format("%s has remove from inventory", type));
            
            return idCollection.remove(itemId);
        }
        return false;
    }

    /**
     * return Entity InventoryList
     * 
     * @return List<Entity>
     */
    public List<Entity> getAllInventory() {
        List<Entity> inventoryList = new ArrayList<>();
        for(List<Entity> itemList : inventory.values()){
            inventoryList.addAll(itemList);
        }
        return inventoryList;
    }
    public boolean hasWeapons() {
        return getAllInventory().stream().anyMatch(entity -> entity instanceof BonusDamageStrategy);
    }
    
    public boolean hasItem(String type, int amount) {
        return inventory.containsKey(type) && inventory.get(type).size() >= amount;
    }

    public int countItem(String type) {
        if (inventory.containsKey(type)) {
            return inventory.get(type).size();
        }
        return 0;
    }

    public List<Entity> getItems(String type) {
        inventory.keySet().stream().forEach(str -> System.out.println(str));
        List<Entity> items = inventory.get(type);
        System.out.println(items == null);
        if (items == null) {
            return new LinkedList<>();
        }
        return items;
    } 
    public void print() {
        idCollection.Keys().stream().forEach(key -> System.out.println(key));
    }
    public HashMap<String, List<Entity>> getInventory() {
        return inventory;
    }
    public InventoryViewer view() {
        return new InventoryViewer(this);
    }
}
