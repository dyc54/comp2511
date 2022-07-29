package dungeonmania.buildableEntities;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;

public abstract class buildableEntity extends Entity{

    public buildableEntity(String type) {
        super(type);
    }

    public buildableEntity(String type, int x, int y) {
        super(type, x, y);
    }

    public buildableEntity(String type, String id) {
        super(type);
        setEntityId(id);
    }

    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
    
}

