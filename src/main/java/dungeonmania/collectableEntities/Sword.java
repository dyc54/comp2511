package dungeonmania.collectableEntities;

import dungeonmania.Durability;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;

public class Sword extends CollectableEntity implements BonusDamageAdd, Durability{
    private double sword_attack;
    private int sword_durability;

    public Sword(String type, int x, int y, int sword_durability, double sword_attack) {
        super(type, x, y);
        this.sword_attack = sword_attack;
        this.sword_durability = sword_durability;
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

    public void setDurability() {
        this.sword_durability -= 1;
    }

    public boolean checkDurability(){
        return sword_durability == 0;
    }
    
}
