package dungeonmania.buildableEntities;

import dungeonmania.Durability;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class Shield extends BuildableEntity implements BonusDefenceAdd, Durability {
    private  final double shieldDefence;
    private int shieldDurability;

    public Shield(String type, double shieldDefence, int shieldDurability, String id) {
        super(type, id);
        this.shieldDefence = shieldDefence;
        this.shieldDurability = shieldDurability;

    }
    public double getShield_defence() {
        return shieldDefence;
    }

    @Override
    public double defence() {
        return shieldDefence;
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
        this.shieldDurability -= 1;
    }

    @Override
    public boolean checkDurability() {
        return shieldDurability == 0;
    }

}
