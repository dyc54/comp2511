package dungeonmania.CollectableEntities.DurabilityEntities;

import dungeonmania.CollectableEntities.CollectableEntity;

public abstract class DurabilityEntity extends CollectableEntity{
    private int durability;

    public DurabilityEntity(String type, int durability, int x, int y) {
        super(type, x, y);
        this.durability = durability;
    }
    public DurabilityEntity(String type, int durability) {
        super(type);
        this.durability = durability;
    }
    public void setDurability() {
        this.durability -= 1;
    }

    public boolean checkDurability(){
        return durability < 0;
    }
}
