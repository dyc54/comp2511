package dungeonmania.collectableEntities.durabilityEntities;

import dungeonmania.Durability;
import dungeonmania.collectableEntities.CollectableEntity;
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
        System.out.println(String.format("Item %s DUration %d -> %d", getEntityId(), sword_durability, sword_durability - 1));
        this.sword_durability -= 1;
    }

    public boolean checkDurability(){
        System.out.println(String.format("Item %s DUration %d ", getEntityId(), sword_durability));
        
        return sword_durability == 0;
    }
    
}
