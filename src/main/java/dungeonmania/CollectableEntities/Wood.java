package dungeonmania.CollectableEntities;

public class Wood extends CollectableEntity{

    private int shield_durability;
    private int shield_defence;

    public Wood(String type, int x, int y, int shield_durability, int shield_defence) {
        super(type, x, y);
        this.shield_durability = shield_durability;
        this.shield_defence = shield_defence;
    }
    
    public int getShield_durability() {
        return shield_durability;
    }
    
    public int getShield_defence() {
        return shield_defence;
    }
}
