package dungeonmania.buildableEntities;

import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class MidnightArmour extends BuildableEntity implements BonusDamageAdd, BonusDefenceAdd{

    private double attack;
    private double defence;
    public MidnightArmour(String type, double attack, double defence, String id) {
        super(type, id);
        this.attack = attack;
        this.defence = defence;
    }

    @Override
    public double damage() {
        return attack;
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        return obj == this;
    }

    @Override
    public double defence() {
        return defence;
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
