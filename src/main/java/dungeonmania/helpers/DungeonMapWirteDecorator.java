package dungeonmania.helpers;

import java.util.Collection;

import javax.swing.text.PlainDocument;

import dungeonmania.Entity;
import dungeonmania.Player;

public class DungeonMapWirteDecorator extends DungeonMapDecorator{

    public DungeonMapWirteDecorator(DungeonMap map) {
        super(map);
    }

    public void remove(Entity entity) {
        map.removeEntity(entity);
    }

    public void remove(Location location, int radius) {
        Collection<Entity> removed =  map.getEntities(location, radius);
        removed.removeIf(entity -> entity instanceof Player);
        removed.stream().forEach(entity -> remove(entity));
        // map.UpdateAllEntities();
    }

    public void add(Entity entity) {
        map.addEntity(entity);
    }
    public void update(Entity entity) {
        map.UpdateEntity(entity);
    }
    public void updateLogicalEntities() {
        map.UpdateAllLogicalEntities();
    }
}
