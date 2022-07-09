package dungeonmania.BuildableEntities;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;

public abstract class BuildableEntity extends Entity{
    private int durability;

    public BuildableEntity(String id,String type,int durability){
        setType(type);
        setEntityId(id);
        this.durability = durability;
    }

    public void setDurability() {
        this.durability -= 1;
    }

    public int getDurability() {
        return durability;
    }

    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
}
