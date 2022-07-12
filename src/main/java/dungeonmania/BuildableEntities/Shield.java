package dungeonmania.BuildableEntities;

import dungeonmania.Strategies.DefenceStrategies.BonusDefenceAdd;

public class Shield extends BuildableEntity implements BonusDefenceAdd{
    private  final double shield_defence;

    public Shield(String id, String type, int shield_defence, int shield_durability) {
        super(id, type, shield_durability);
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
