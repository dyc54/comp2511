package dungeonmania.staticEntities;

import dungeonmania.Entity;

public class SwampTile extends StaticEntity{

    int movement_factor;

    public SwampTile(String type, int x, int y, int movement_factor) {
        super(type, x, y);
        this.movement_factor = movement_factor;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }
    
}
