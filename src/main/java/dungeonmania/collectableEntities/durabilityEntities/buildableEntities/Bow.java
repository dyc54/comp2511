package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.collectableEntities.durabilityEntities.DurabilityEntity;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageMul;

public class Bow extends DurabilityEntity implements BonusDamageMul{
    private static final int attack = 2;

    public Bow(String type, int Bow_durability) {
        super(type, Bow_durability);
    }
    public Bow(String id, String type, int Bow_durability) {
        super(type, Bow_durability, id);
    }

    public static int getAttack() {
        return attack;
    }

    @Override
    public double damage() {
        return getAttack();
    }

    @Override
    public boolean equals(BonusDamageMul obj) {
        return obj == this;
    }
    @Override
    public ItemResponse toItemResponse() {
        return getItemResponse();
    }



}
