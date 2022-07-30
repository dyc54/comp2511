package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.MovementFactor;
import dungeonmania.helpers.DungeonMap;

public class SwampTile extends StaticEntity{

    private int movementFactor;

    public SwampTile(String type, int x, int y, int movementFactor) {
        super(type, x, y);
        this.movementFactor = movementFactor;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // do nothing by defalut
        if (entity instanceof MovementFactor) {
            MovementFactor Entity = (MovementFactor) entity;
            Entity.resetMovementFactor(movementFactor);
        }
        return false;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return true;
    }

    public int getMovementFactor() {
        return movementFactor;
    }
    
}
