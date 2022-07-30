package dungeonmania.buildableEntities;

import dungeonmania.Entity;
import dungeonmania.response.models.ItemResponse;

public abstract class BuildableEntity extends Entity{

    public BuildableEntity(String type) {
        super(type);
    }

    public BuildableEntity(String type, int x, int y) {
        super(type, x, y);
    }

    public BuildableEntity(String type, String id) {
        super(type);
        setEntityId(id);
    }

    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
    
}

