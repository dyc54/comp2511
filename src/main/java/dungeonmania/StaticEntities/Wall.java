package dungeonmania.StaticEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.Location;

public class Wall extends StaticEntity {


    public Wall(String type, Location location) {
        super(type, location);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }


}
