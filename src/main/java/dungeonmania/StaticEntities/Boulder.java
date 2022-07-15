package dungeonmania.StaticEntities;

import dungeonmania.Entity;
import dungeonmania.Interactability;
import dungeonmania.Player;
import dungeonmania.MovingEntities.Spider;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity implements Interactability {
    public Boulder(String type, int x, int y) {
        super(type, x, y);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
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
            // Get the moving direction
            Player player = (Player) entity;
            // Position direction = Location.getMoveDir(player.getPreviousLocation(),
            // player.getLocation());
            Location next = getLocation().getLocation(player.getDirection());
            // Location next = new Location(player.getLocation().getX() + direction.getX(),
            // player.getLocation().getY() + direction.getY());
            if (DungeonMap.isaccessible(map, next, this)) {
                // set the position of player
                if (!entity.getLocation().equals(getLocation())
                        && DungeonMap.isaccessible(map, getLocation(), entity)) {
                    entity.setLocation(getLocation());
                }
                // set the new position of the boulder
                setLocation(next);
                // Interact with entities in the next position. i.e. switch
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
        // do nothing by defalut
        // if (entity instanceof Player)
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }

}
