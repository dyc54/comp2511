package dungeonmania.StaticEntities;

import dungeonmania.Entity;

public class OpenedDoor extends StaticEntity {

    public OpenedDoor(String type, int x, int y) {
        super(type, x, y);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }

}
