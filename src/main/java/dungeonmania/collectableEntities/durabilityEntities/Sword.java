package dungeonmania.collectableEntities.durabilityEntities;

import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;

public class Sword extends DurabilityEntity implements BonusDamageAdd{
    private double sword_attack;

    public Sword(String type, int x, int y, int sword_durability, double sword_attack) {
        super(type, sword_durability, x, y);
        this.sword_attack = sword_attack;
    }

    public double getSword_attack() {
        return sword_attack;
    }

    @Override
    public double damage() {
        return getSword_attack();
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        return false;
    }
    @Override
    public ItemResponse toItemResponse() {
        return getItemResponse();
    }
    
}
