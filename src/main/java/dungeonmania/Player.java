package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.response.models.ItemResponse;

public class Player extends Entity{
    private int attack;
    private int health;
    private List<Entity> inventoryList;

    public Player(String type, int x, int y, int attack, int health){
        this.attack = attack;
        this.health = health;
        setType(type);
        setLocation(x, y);
        setEntityId("player1");// 现在还不是唯一
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public List<Entity> getInventoryList() {
        return inventoryList;
    }

    public void addInventoryList(Entity item) {
        inventoryList.add(item);
    }

    public void removeInventoryList(Entity item) {
        inventoryList.remove(item);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public List<ItemResponse> getItemResponse() {
        List<ItemResponse> inventory = new ArrayList<>();
        for(Entity item : inventoryList){
            inventory.add(new ItemResponse(item.getEntityId(), item.getType()));
        }
        return inventory;
    }

}
