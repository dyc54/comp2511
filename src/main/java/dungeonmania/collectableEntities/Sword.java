package dungeonmania.collectableEntities;

import dungeonmania.Durability;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;

public class Sword extends CollectableEntity implements BonusDamageAdd, Durability{
    private double swordAttack;
    private int swordDurability;

    public Sword(String type, int x, int y, int swordDurability, double swordAttack) {
        super(type, x, y);
        this.swordAttack = swordAttack;
        this.swordDurability = swordDurability;
    }

    public double getSword_attack() {
        return swordAttack;
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
        this.swordDurability -= 1;
    }

    public boolean checkDurability(){
        return swordDurability == 0;
    }
    
}
