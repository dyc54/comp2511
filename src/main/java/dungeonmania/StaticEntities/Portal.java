package dungeonmania.staticEntities;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    private final String color;

    public Portal(String type, int x, int y, String color) {
        super(type, x, y);
        this.color = color;
    }

    public String getColour() {
        return this.color;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // DO nothing by defalut
        boolean hasChain = false;
        if (entity instanceof Player || entity instanceof Mercenary) {
            if (map.getEntities("portal")
                    .stream()
                    .noneMatch(portal -> ((Portal) portal).getColour().equals(color) && !portal.equals(this))) {
                return hasChain;
            }
            // Get the target portal
            Location target = map.getEntities("portal")
                    .stream()
                    .filter(portal -> ((Portal) portal).getColour().equals(color) && !portal.equals(this))
                    .collect(Collectors.toList()).get(0).getLocation();
            System.out
                    .println(String.format("portal: tp %s ->  %s", entity.getLocation().toString(), target.toString()));
            // Get the position behind the target portal
            Location entryLocation = entity.getLocation();
            Position p = Location.getMoveDir(entryLocation, getLocation());
            Location next = target.getLocation(p);
            if (DungeonMap.isaccessible(map, target, entity)) {
                // If the position behind the portal has interactable entity, interact with it
                if (DungeonMap.hasInteractableEntity(map, next, entity)) {
                    hasChain = true;
                    entity.setLocation(target);
                } else {
                    // If no interactable entity, just teleport to the position
                    if (DungeonMap.isaccessible(map, next, entity)) {
                        entity.setLocation(next);
                    } else if (entity instanceof Player) {
                        // else just stay still
                        Player player = (Player) entity;
                        player.setStay(true);
                    }
                }
            }
        }
        return hasChain;
    }

    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap map) {
        // do nothing by defalut
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }
}
