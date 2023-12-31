package dungeonmania;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Position;

import java.util.UUID;

import org.json.JSONObject;

import dungeonmania.helpers.Location;

public abstract class Entity{
    private Location location;
    private String EntityId;
    private String type;
    private int count;
    private String newID(String type){
        return type + UUID.randomUUID();
    }
    
    public Entity(String type, Location location) {
        this.EntityId = newID(type);
        this.type = type;
        this.location = location;
        count = (count == 0 ? 0: count++);
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
        this.location.setLocation(location);
    }

    public void setRandomId() {
        this.EntityId = newID(type);
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

    @Override 
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof Entity) {
            return ((Entity) obj).getEntityId().equals(EntityId);
        } else if (obj instanceof String) {
            return ((String) obj).equals(EntityId);

        }
        return false;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("type", type);
        obj.put("x", location.getX());
        obj.put("y", location.getY());
        obj.put("id", EntityId);
        return obj;
    }

    @Override
    public int hashCode() {
        return EntityId.hashCode();
    }
    public static <T extends Entity, Y extends Entity> boolean equals(T entity1, Y entity2) {
        return ((Entity) entity1).equals((Entity) entity2);
    }
    public static <T extends Entity> boolean equals(T entity1, String entity2) {
        return ((Entity) entity1).equals(entity2);
    }
    
}
