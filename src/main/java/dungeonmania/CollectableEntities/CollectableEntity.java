package dungeonmania.CollectableEntities;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;

public abstract class CollectableEntity extends Entity{

    public CollectableEntity(String id,String type, int x, int y){
        setType(type);
        setLocation(x, y);
        setEntityId(id);
    }
    
    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
}
