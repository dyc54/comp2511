package dungeonmania;

import dungeonmania.helpers.DungeonMap;

public interface Interactability {
    public boolean interact(Entity entity, DungeonMap  map);
    public boolean hasSideEffect(Entity entity, DungeonMap  map);
}
