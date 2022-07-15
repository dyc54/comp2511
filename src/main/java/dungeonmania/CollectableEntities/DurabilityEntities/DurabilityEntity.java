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
        System.out.println(String.format("Item %s DUration %d -> %d", getEntityId(), durability, durability - 1));
        this.durability -= 1;
    }

    public boolean checkDurability(){
        System.out.println(String.format("Item %s DUration %d ", getEntityId(), durability));
        
        return durability == 0;
    }
}
