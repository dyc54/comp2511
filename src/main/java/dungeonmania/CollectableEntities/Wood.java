package dungeonmania.CollectableEntities;

public class Wood extends CollectableEntity{

    private int shield_durability;

    public Wood(String type, int x, int y, int shield_durability) {
        super(type, x, y);
        this.shield_durability = shield_durability;
    }
    
    public int getShield_durability() {
        return shield_durability;
    }
    
}
