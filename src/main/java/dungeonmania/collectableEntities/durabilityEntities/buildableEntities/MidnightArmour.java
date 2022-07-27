package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.Entity;
import dungeonmania.collectableEntities.durabilityEntities.DurabilityEntity;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class MidnightArmour extends DurabilityEntity implements BonusDamageAdd, BonusDefenceAdd{

    private int attack;
    private int defence;
    public MidnightArmour(String type, int attack, int defence, String id) {
        super(type, 99999);
        this.attack = attack;
        this.defence = defence;
        setEntityId(id);
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
    
}
