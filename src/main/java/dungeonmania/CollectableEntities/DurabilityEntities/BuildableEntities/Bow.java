package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import dungeonmania.CollectableEntities.DurabilityEntities.DurabilityEntity;

public class Bow extends DurabilityEntity{
    private static final int attack = 2;

    public Bow(String type, int Bow_durability) {
        super(type, Bow_durability);
    }

    public static int getAttack() {
        return attack;
    }

}
