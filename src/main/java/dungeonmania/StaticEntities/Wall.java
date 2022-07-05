package dungeonmania.StaticEntities;

import dungeonmania.helpers.Location;

public class Wall extends StaticEntity {
    private Location location;
    private String type;
    public Wall (String type, Location location) {
        this.location = location;
        this.type = type;
    }
    public Location getLocation() {
        return location;
    }
    
    public String getType() {
        return type;
    }
    
}
