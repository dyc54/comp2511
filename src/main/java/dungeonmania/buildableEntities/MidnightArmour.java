package dungeonmania.buildableEntities;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class MidnightArmour extends buildableEntity implements BonusDamageAdd, BonusDefenceAdd{

    private double attack;
    private double defence;
    public MidnightArmour(String type, double attack, double defence, String id) {
        super(type, id);
        this.attack = attack;
        this.defence = defence;
    }

    @Override
    public double damage() {
        // TODO Auto-generated method stub
        return attack;
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        // TODO Auto-generated method stub
        return obj == this;
    }

    @Override
    public double defence() {
        // TODO Auto-generated method stub
        return defence;
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public ItemResponse toItemResponse() {
        return getItemResponse();
    }
    
}
