package dungeonmania.timeTravel;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.staticEntities.StaticEntity;

public class TimeTravellingPortal extends StaticEntity{

    public TimeTravellingPortal(String type, Location location) {
        super(type, location);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return true;
    }
    
    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // do nothing by defalut
        return false;
    }
}
