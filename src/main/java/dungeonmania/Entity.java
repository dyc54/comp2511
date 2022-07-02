package dungeonmania;

import dungeonmania.helpers.Location;

public abstract class Entity implements Observer{
    // TODO: 
    Subject subject;
    private Location location;
    private String EntityId;
    // public
    public Location getLocation() {
        return location;
    }
    public String getEntityId() {
        return EntityId;
    }
    @Override
    public String toString() {
        return "Entity::toString";
    }
    public void update(Subject s) {
        // update(obj);
        // TODO: DO SOMETHING
    }
    // public static abstract Entity NewEntity(int x, int y, String type);
}
