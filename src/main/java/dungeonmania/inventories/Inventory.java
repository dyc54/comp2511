package dungeonmania.inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.*;
import dungeonmania.helpers.IdCollection;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.attackStrategies.BonusDamageMul;
import dungeonmania.strategies.attackStrategies.BonusDamageStrategy;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class Inventory implements Iterable<Entity> {
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
        if (item instanceof BonusDamageAdd) {
            player.getAttackStrategy().bonusDamage((BonusDamageAdd) item);
        } 
        if (item instanceof BonusDamageMul) {
            player.getAttackStrategy().bonusDamage((BonusDamageMul) item);
        } 
        if (item instanceof BonusDefenceAdd) {
            player.getDefenceStrayegy().bonusDefence((BonusDefenceAdd) item);
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
    public boolean removeFromInventoryList(String type, int number, Player player) {
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
            if (player != null) {
                if (item instanceof BonusDamageAdd) {
                    player.getAttackStrategy().removeBounus((BonusDamageAdd) item);
                } 
                if (item instanceof BonusDamageMul) {
                    player.getAttackStrategy().removeBounus((BonusDamageMul) item);
                } 
                if (item instanceof BonusDefenceAdd) {
                    player.getDefenceStrayegy().removeDefence((BonusDefenceAdd) item);
                }
            }
            inventory.get(type).remove(item);
            
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
        List<Entity> items = inventory.get(type);
        if (items == null) {
            return new LinkedList<>();
        }
        return items;
    } 

    public HashMap<String, List<Entity>> getInventory() {
        return inventory;
    }

    public InventoryViewer view() {
        return new InventoryViewer(this);
    }

    @Override
    public Iterator<Entity> iterator() {
        getAllInventory().iterator();
        return null;
    }

    public Stream<Entity> stream() {
        return getAllInventory().stream();
    }
}
