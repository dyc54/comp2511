package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.MovementFactor;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;

public class SwampTile extends StaticEntity{

    private int movementFactor;

    public SwampTile(String type, int x, int y, int movement_factor) {
        super(type, x, y);
        this.movementFactor = movement_factor;
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
        // TODO Auto-generated method stub
        return true;
    }

    public int getMovementFactor() {
        return movementFactor;
    }
    
}
