package dungeonmania.BuildableEntities;

public class Shield extends BuildableEntity{
    private int shield_defence;

    public Shield(String id, String type, int shield_defence, int shield_durability) {
        super(id, type, shield_durability);
        this.shield_defence = shield_defence;
    }
    
    public void setShield_defence(int shield_defence) {
        this.shield_defence = shield_defence;
    }

    public int getShield_defence() {
        return shield_defence;
    }
   
}
