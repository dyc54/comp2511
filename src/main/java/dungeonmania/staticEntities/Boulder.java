package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.Interactability;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.Spider;

public class Boulder extends StaticEntity {
    public Boulder(String type, int x, int y) {
        super(type, x, y);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        if (entity instanceof Spider) {
            return false;
        }
        return false;
    }

    /**
     * move the boulder to the same direction as player moves
     * 
     * @param Entity     entity
     * @param DungeonMap map
     * @return boolean
     */
    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            Location next = getLocation().getLocation(player.getDirection());
            if (DungeonMap.isaccessible(map, next, this)) {
                if (!entity.getLocation().equals(getLocation())
                        && DungeonMap.isaccessible(map, getLocation(), entity)) {
                    entity.setLocation(getLocation());
                }
                setLocation(next);
                map.getEntities(next).forEach(element -> {
                    if (element instanceof Interactability) {
                        Interactability interactableEntity = (Interactability) element;
                        interactableEntity.interact(this, map);
                    }
                });

            }
        }
        return false;
    }

    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap map) {
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }

}
