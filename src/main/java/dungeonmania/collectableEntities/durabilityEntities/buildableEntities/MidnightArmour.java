package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class MidnightArmour implements BonusDamageAdd, BonusDefenceAdd{

    @Override
    public double damage() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double defence() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
