package dungeonmania.buildableEntities;

import dungeonmania.Durability;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class Shield extends buildableEntity implements BonusDefenceAdd, Durability {
    private  final double shield_defence;
    private int shield_durability;

    public Shield(String type, double shield_defence, int shield_durability, String id) {
        super(type, id);
        this.shield_defence = shield_defence;
        this.shield_durability = shield_durability;

    }
    public double getShield_defence() {
        return shield_defence;
    }

    @Override
    public double defence() {
        return shield_defence;
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        return this == obj;
    }

    @Override
    public ItemResponse toItemResponse() {
        return getItemResponse();
    }

    @Override
    public void setDurability() {
        this.shield_durability -= 1;
    }

    @Override
    public boolean checkDurability() {
        return shield_durability == 0;
    }

}
