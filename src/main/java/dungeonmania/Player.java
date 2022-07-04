package dungeonmania;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Player extends Entity{
    private int attack;
    private int health;
    private List<Entity> inventoryList;
    private int x;
    private int y;

    public Player(String type, int x, int y, int attack, int health){
        this.attack = attack;
        this.health = health;
        this.x = x;
        this.y = y;
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

  
    public void setLocation(Position p) {
        super.setLocation(x+p.getX(), y+p.getY());
        this.x = x+p.getX();
        this.y = y+p.getY();
    }

}
