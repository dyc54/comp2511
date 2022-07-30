package dungeonmania.buildableEntities;

import dungeonmania.Durability;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageMul;

public class Bow extends buildableEntity implements BonusDamageMul, Durability{
    private static final int attack = 2;
    private int Bow_durability;

    public Bow(String type, int Bow_durability) {
        super(type);
        this.Bow_durability = Bow_durability;
    }
    public Bow(String id, String type, int Bow_durability) {
        super(type, id);
        this.Bow_durability = Bow_durability;
    }

    public static int getAttack() {
        return attack;
    }

    @Override
    public double damage() {
        return getAttack();
    }

    @Override
    public boolean equals(BonusDamageMul obj) {
        return obj == this;
    }

    @Override
    public ItemResponse toItemResponse() {
        return getItemResponse();
    }

    @Override
    public void setDurability() {
        this.Bow_durability -= 1;
    }
    
    @Override
    public boolean checkDurability() {
        return Bow_durability == 0;
    }



}
