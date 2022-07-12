package dungeonmania.BuildableEntities;

import dungeonmania.Strategies.AttackStrategies.BonusDamageMul;

public class Bow extends BuildableEntity implements BonusDamageMul{
    private static final double BOW_DAMAGE = 2.0;
    public Bow(String id, String type, int Bow_durability) {
        super(id, type, Bow_durability);
    }

    @Override
    public double damage() {
        return BOW_DAMAGE;
    }

    @Override
    public boolean equals(BonusDamageMul obj) {
        return this == obj;
    }

}
