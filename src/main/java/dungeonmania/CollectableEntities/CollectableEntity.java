package dungeonmania.CollectableEntities;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;

public abstract class CollectableEntity extends Entity{

    public CollectableEntity(String id,String type){
        setType(type);
        setEntityId(id);
    }
    
    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
}
