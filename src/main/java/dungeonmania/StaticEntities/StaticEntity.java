package dungeonmania.staticEntities;

import dungeonmania.Accessibility;
import dungeonmania.Entity;
import dungeonmania.Interactability;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public abstract class StaticEntity extends Entity implements Accessibility, Interactability {
    public StaticEntity(String type, int x, int y) {
        super(type, x, y);
    }

    public StaticEntity(String type, Location location) {
        super(type, location);
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // do nothing by defalut
        return false;
    }

    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap map) {
        // do nothing by defalut
        return false;
    }
}
