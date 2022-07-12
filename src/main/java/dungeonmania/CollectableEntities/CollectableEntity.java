package dungeonmania.CollectableEntities;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;

public abstract class CollectableEntity extends Entity{

    public CollectableEntity(String type, int x, int y){
        super(type, x, y);
    }
    
    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
}
