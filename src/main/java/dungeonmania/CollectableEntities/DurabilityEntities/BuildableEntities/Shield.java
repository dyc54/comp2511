package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import dungeonmania.CollectableEntities.DurabilityEntities.DurabilityEntity;

public class Shield extends DurabilityEntity{
    private int shield_defence;

    public Shield(String id, String type, int shield_defence, int shield_durability) {
        super(id, type, shield_durability);
        this.shield_defence = shield_defence;
    }

    public int getShield_defence() {
        return shield_defence;
    }
   
}
