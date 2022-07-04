package dungeonmania;

public class Player extends Entity{
    private int attack;
    private int health;

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

    public void setHealth(int health) {
        this.health = health;
    }
    

}
