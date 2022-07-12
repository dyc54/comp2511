package dungeonmania;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

import java.text.SimpleDateFormat;

import dungeonmania.helpers.Location;

public abstract class Entity{
    // TODO: 
    // Subject subject;
    private Location location;
    private String EntityId;
    private String type;
    private String newID(String type){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        return type+sdf.format(System.currentTimeMillis());
    }
    public Entity(String type, Location location) {
        this.EntityId = newID(type);
        this.type = type;
        this.location = location;
    }
    public Entity(String type, int x, int y) {
        this(type, Location.AsLocation(x, y));
    }
    public Entity(String type) {
        this(type, null);
    }
    public Location getLocation() {
        return location;
    }
    public String getEntityId() {
        return EntityId;
    }
    
    public void setLocation(int x, int y) {
        setLocation(Location.AsLocation(x, y));
    }

    public void setLocation(Location location) {
        this.location.setLocation(location);
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
        return String.format("%s, %s, %s", location.toString(), type, EntityId);
    }
    
    public String getType() {
        return type;
    }

    // public static abstract Entity NewEntity(int x, int y, String type);
}
