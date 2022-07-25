package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.Location;

public class Wall extends StaticEntity {

    public Wall(String type, Location location) {
        super(type, location);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return false;
    }

}
