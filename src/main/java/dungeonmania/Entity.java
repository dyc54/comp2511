package dungeonmania;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

import dungeonmania.helpers.Location;

public abstract class Entity implements Observer{
    // TODO: 
    Subject subject;
    private Location location;
    private String EntityId;
    private String type;
    // public

    public Location getLocation() {
        return location;
    }
    public String getEntityId() {
        return EntityId;
    }
    
    public void setLocation(int x, int y) {
        location =  new Location(x, y);
    }

    public void setEntityId(String EntityId) {
        this.EntityId = EntityId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EntityResponse getEntityResponse() {
        return new EntityResponse(getEntityId(), getType(), new Position(getLocation().getX(), getLocation().getY()), false);
    }


    @Override
    public String toString() {
        return "Entity::toString";
    }
    public void update(Subject s) {
        // update(obj);
        // TODO: DO SOMETHING
    }
    public String getType() {
        return type;
    }
    // public static abstract Entity NewEntity(int x, int y, String type);
}
