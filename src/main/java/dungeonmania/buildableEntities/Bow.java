package dungeonmania.buildableEntities;

import dungeonmania.Durability;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageMul;

public class Bow extends BuildableEntity implements BonusDamageMul, Durability{
    private static final int attack = 2;
    private int BowDurability;

    public Bow(String type, int BowDurability) {
        super(type);
        this.BowDurability = BowDurability;
    }
    public Bow(String id, String type, int BowDurability) {
        super(type, id);
        this.BowDurability = BowDurability;
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
        this.BowDurability -= 1;
    }
    
    @Override
    public boolean checkDurability() {
        return BowDurability == 0;
    }
}
