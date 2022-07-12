package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import dungeonmania.Strategies.DefenceStrategies.BonusDefenceAdd;
import dungeonmania.CollectableEntities.DurabilityEntities.DurabilityEntity;

public class Shield extends DurabilityEntity implements BonusDefenceAdd {
    private  final double shield_defence;

    public Shield(String type, int shield_defence, int shield_durability) {
        super(type, shield_durability);
        this.shield_defence = shield_defence;
    }
    // public void setShield_defence(int shield_defence) {
    //     this.shield_defence = shield_defence;
    // }
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

}
