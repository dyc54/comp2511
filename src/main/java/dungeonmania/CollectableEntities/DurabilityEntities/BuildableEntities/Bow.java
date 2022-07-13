package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import dungeonmania.CollectableEntities.DurabilityEntities.DurabilityEntity;
import dungeonmania.Strategies.AttackStrategies.BonusDamageMul;

public class Bow extends DurabilityEntity implements BonusDamageMul{
    private static final int attack = 2;

    public Bow(String type, int Bow_durability) {
        super(type, Bow_durability);
    }

    public static int getAttack() {
        return attack;
    }

    @Override
    public double damage() {
        // TODO Auto-generated method stub
        return getAttack();
    }

    @Override
    public boolean equals(BonusDamageMul obj) {
        // TODO Auto-generated method stub
        return obj == this;
    }



}
