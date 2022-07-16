package dungeonmania.collectableEntities.durabilityEntities;

import dungeonmania.strategies.attackStrategies.BonusDamageAdd;

public class Sword extends DurabilityEntity implements BonusDamageAdd{
    private int sword_attack;

    public Sword(String type, int x, int y, int sword_durability, int sword_attack) {
        super(type, sword_durability, x, y);
        this.sword_attack = sword_attack;
    }

    public int getSword_attack() {
        return sword_attack;
    }

    @Override
    public double damage() {
        // TODO Auto-generated method stub
        return getSword_attack();
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        // TODO Auto-generated method stub
        return false;
    }

    
}
