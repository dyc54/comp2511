package dungeonmania.CollectableEntities;

public class Sword extends CollectableEntity{
    private int sword_attack;
    private int sword_durability;

    public Sword(String id, String type, int x, int y, int sword_attack, int sword_durability) {
        super(id, type, x, y);
        this.sword_attack = sword_attack;
        this.sword_durability = sword_durability;
    }

    public void setSword_durability() {
        this.sword_durability -= 1;
    }

    public int getSword_attack() {
        return sword_attack;
    }

    public int getSword_durability() {
        return sword_durability;
    }
    
}
