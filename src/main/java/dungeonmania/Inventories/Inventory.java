package dungeonmania.Inventories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import dungeonmania.Entity;
import dungeonmania.CollectableEntities.*;
import dungeonmania.CollectableEntities.DurabilityEntities.DurabilityEntity;
import dungeonmania.CollectableEntities.DurabilityEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.DurabilityEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.DurabilityEntities.PotionEntity;
import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.Bow;
import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.Shield;
import dungeonmania.helpers.Config;

public class Inventory {
    private final HashMap<String, List<Entity>> inventory = new HashMap<>(); //key 是实体的类型
    private List<Entity> BattleInventoryList = new ArrayList<>();

    /**
     * add Entity to InventoryList, key is Entity type
     * 
     * @param Entity item
     */
    public void addToInventoryList(Entity item) {
        System.out.println(String.format("%s has been picked up", item.getType()));
        if(inventory.containsKey(item.getType())){
            inventory.get(item.getType()).add(item);
        }else{
            List<Entity> itemList = new ArrayList<>();
            itemList.add(item);
            inventory.put(item.getType(), itemList);
        }
        inventory.keySet().stream().forEach(str -> System.out.println(str));

    }

    /**
     * remove Entity from InventoryList by Entity
     * 
     * @param Entity item
     */
    public void removeFromInventoryList(Entity item) {
        inventory.get(item.getType()).remove(item);
    }

    public boolean removeFromInventoryList(String type, int number) {
        if (!inventory.containsKey(type)) {
            return false;
        }
        List<Entity> items = inventory.get(type);
        if (items.size() < number) {
            return false;
        } else {
            for(int i = 0; i < number; i++) {
                items.remove(0);
            }
        }
        return true;
    }

    /**
     * remove Entity from InventoryList by Entity id
     * 
     * @param itemId 
     */
    public void removeFromInventoryList(int itemId) {
        HashMap<String, List<Entity>> inventoryCopy = new HashMap<>(inventory);
        for(Entry<String, List<Entity>> entry : inventoryCopy.entrySet()) {
                for(Entity item : entry.getValue()){
                    if(item.getEntityId().equals(itemId)){
                        inventory.get(entry.getKey()).remove(item);
                        System.out.println(String.format("%s has been remove from inventory", item.getType()));

                    }
                }
          }     
    }

    /**
     * return Entity InventoryList
     * 
     * @return List<Entity>
     */
    public List<Entity> getInventoryList() {
        List<Entity> inventoryList = new ArrayList<>();
        for(List<Entity> itemList : inventory.values()){
            inventoryList.addAll(itemList);
        }
        return inventoryList;
    }

    /**
     * return Entity BattleInventoryList,this list stored weapons Entity 
     * 
     * @return List<Entity>
     */
    public List<Entity> getBattleInventoryList() {
        if(inventory.containsKey("swod")){
            BattleInventoryList.addAll(inventory.get("swod"));
        }

        if(inventory.containsKey("bow")){
            BattleInventoryList.addAll(inventory.get("bow"));
        }

        if(inventory.containsKey("shield")){
            BattleInventoryList.addAll(inventory.get("shield"));
        }
        return BattleInventoryList;
    }

    /**
     * return Player Status, [Invincibility] or [Invisibility] or [Invisibility, Invincibility]
     * it also check Potion durability if potion durability = 0 ,remove From InventoryList
     * 
     * @return List<String>
     */
    public List<String> checkPlayerStatus(){
        List<String> playerStatus = new ArrayList<>();
        if(inventory.get("invincibility_potion") != null && inventory.get("invincibility_potion").size() > 0){
            InvincibilityPotion invincibilityPotion = (InvincibilityPotion)inventory.get("invincibility_potion").get(0);
            if(invincibilityPotion.getEffect() != null && !invincibilityPotion.checkDurability()){
                playerStatus.add(invincibilityPotion.getEffect());
            }else if(invincibilityPotion.checkDurability()){
                inventory.get("invincibility_potion").remove(0);
            }
        }

        if(inventory.get("invisibility_potion") != null && inventory.get("invisibility_potion").size() > 0){
            InvisibilityPotion invisibilityPotion = (InvisibilityPotion)inventory.get("invisibility_potion").get(0);
            if(invisibilityPotion.getEffect() != null && !invisibilityPotion.checkDurability()){
                playerStatus.add(invisibilityPotion.getEffect());
            }else if(invisibilityPotion.checkDurability()){
                inventory.get("invisibility_potion").remove(0);
            }
        }
        return playerStatus;
    }

    /**
     * player use potion,or player use bomb(not to do)
     * 
     * @param itemId 
     */
    public void useItem(String itemId){
        HashMap<String, List<Entity>> inventoryCopy = new HashMap<>(inventory);
        for(Entry<String, List<Entity>> entry : inventoryCopy.entrySet()) {
                for(Entity item : entry.getValue()){
                    if(item.getEntityId().equals(itemId)){
                        if(item instanceof PotionEntity){
                            PotionEntity potion = (PotionEntity) item;
                            potion.setInUsing();
                            potion.setDurability();
                        }
                    }
                }
        }
    }

    /**
     * check BattleInventoryList if Durability = 0,remove From InventoryList
     * 
     */
    public void updateBattleInventoryList(){
        for(Entity weapon : getBattleInventoryList()){
            DurabilityEntity item = (DurabilityEntity) weapon;
            if(item.checkDurability()){
                removeFromInventoryList(weapon);
            }else{
                item.setDurability();
            }
        }
    }

    /**
     * return buildable
     * 
     * @return String
     */
    public String build(String buildable, Config config){
        if(buildable.equals("bow") && checkBowMaterial()){
            buildBow(config);
            return "bow";
        }
        
        if(buildable.equals("shield") && checkShieldMaterial()!= null){
            buildShield(checkShieldMaterial(), config);
            return "shield";
        }
        return null;
    }

    /**
     * build Bow add to inventory and remove wood and arrow
     * 
     */
    private void buildBow(Config config){
       Arrows arrows = (Arrows) inventory.get("arrow").get(0);
       addToInventoryList(new Bow("bow", config.bow_durability));
       for (int i = 0; i < 3; i++) {
            inventory.get("arrow").remove(i);
            if(i == 0){
                inventory.get("wood").remove(i);
            }
       }
    }

    /**
     * build Shield add to inventory and remove wood and key or reasure
     * 
     */
    private void buildShield(String str, Config config){
        if(str != null){
            Wood wood = (Wood) inventory.get("wood").get(0);
            addToInventoryList(new Shield("shield", config.shield_defence, config.bow_durability));
            if(str.equals("key")){
                for (int i = 0; i < 2; i++) {
                    inventory.get("wood").remove(i);
                    if(i == 0){
                        inventory.get("key").remove(i);
                    }
                }
            } else {
                for (int i = 0; i < 2; i++) {
                    inventory.get("wood").remove(i);
                    if(i == 0){
                        inventory.get("treasure").remove(i);
                    }
                }
            }
        }
        
    }



    /**
     * Check if there is a sufficient amount of material to build
     * 
     */
    private boolean checkBowMaterial(){
        if(inventory.get("arrow") != null && inventory.get("wood") != null){
            return inventory.get("arrow").size() >= 3 && inventory.get("wood").size() >= 1;
        }
       return false;
    }

    private String checkShieldMaterial(){
        if(inventory.get("wood") == null){
            return null;
        }

        if(inventory.get("key") != null && inventory.get("treasure") != null){
            if(inventory.get("wood").size() >= 2 && (inventory.get("key").size() >= 1 || inventory.get("treasure").size() >= 1)){
                return "treasure";
            }
        }else if(inventory.get("key") != null && inventory.get("treasure") == null){
            if(inventory.get("wood").size() >= 2 && inventory.get("key").size() >= 1){
                return "key";
            }
        }else if(inventory.get("key") == null && inventory.get("treasure") != null){
            if(inventory.get("wood").size() >= 2 && inventory.get("treasure").size() >= 1){
                return "treasure";
            }
        }
       return null;
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

}
