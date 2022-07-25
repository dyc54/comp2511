package dungeonmania.staticEntities;

import dungeonmania.Entity;

public class SwampTile extends StaticEntity{

    public SwampTile(String type, int x, int y) {
        super(type, x, y);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }
    
}
