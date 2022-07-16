package dungeonmania;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

import java.text.SimpleDateFormat;
import java.util.UUID;

import dungeonmania.helpers.Location;

public abstract class Entity{
    // TODO: 
    // Subject subject;
    private Location location;
    private String EntityId;
    private final String type;
    private String newID(String type){
        // UUID id = UUID.randomUUID()
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        // return type+sdf.format(System.currentTimeMillis());
        return type+UUID.randomUUID();
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

    public void setLocation(Location location) {
        System.out.println(location);
        this.location.setLocation(location);
    }

    public void setEntityId(String EntityId) {
        this.EntityId = EntityId;
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

    @Override 
    public boolean equals(Object obj) {
        if (obj instanceof Entity) {
            return ((Entity) obj).getEntityId().equals(EntityId);
        }
        return false;
    }
    // public static abstract Entity NewEntity(int x, int y, String type);
}
