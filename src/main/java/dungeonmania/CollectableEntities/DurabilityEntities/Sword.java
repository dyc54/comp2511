package dungeonmania.CollectableEntities.DurabilityEntities;


public class Sword extends DurabilityEntity{
    private int sword_attack;

    public Sword(String type, int x, int y, int sword_durability, int sword_attack) {
        super(type, sword_durability);
        setLocation(x, y);
        this.sword_attack = sword_attack;
    }

    public int getSword_attack() {
        return sword_attack;
    }
    
}
