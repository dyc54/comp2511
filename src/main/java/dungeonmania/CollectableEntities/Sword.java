package dungeonmania.CollectableEntities;
import dungeonmania.Strategies.AttackStrategies.BonusDamageAdd;;
public class Sword extends CollectableEntity implements BonusDamageAdd {
    private final double sword_attack;
    private int sword_durability;

    public Sword(String id, String type, int x, int y, double sword_attack, int sword_durability) {
        super(id, type, x, y);
        this.sword_attack = sword_attack;
        this.sword_durability = sword_durability;
    }

    public void setSword_durability() {
        this.sword_durability -= 1;
    }

    public double getSword_attack() {
        return sword_attack;
    }

    public int getSword_durability() {
        return sword_durability;
    }

    @Override
    public double damage() {
        return sword_attack;
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        return this == obj;
    }
    
}
