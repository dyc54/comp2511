package dungeonmania.staticEntities;

import java.util.stream.Collectors;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.Mercenary;
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
        return true;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        boolean hasChain = false;
        if (entity instanceof Player || entity instanceof Mercenary) {
            if (map.getEntities("portal")
                    .stream()
                    .noneMatch(portal -> ((Portal) portal).getColour().equals(color) && !portal.equals(this))) {
                return hasChain;
            }
            Location target = map.getEntities("portal")
                    .stream()
                    .filter(portal -> ((Portal) portal).getColour().equals(color) && !portal.equals(this))
                    .collect(Collectors.toList()).get(0).getLocation();
            Location entryLocation = entity.getLocation();
            Position p = Location.getMoveDir(entryLocation, getLocation());
            Location next = target.getLocation(p);
            if (DungeonMap.isaccessible(map, target, entity)) {
                if (DungeonMap.hasInteractableEntity(map, next, entity)) {
                    hasChain = true;
                    entity.setLocation(target);
                } else {
                    if (DungeonMap.isaccessible(map, next, entity)) {
                        entity.setLocation(next);
                    } else if (entity instanceof Player) {
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
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }
    
    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject().put("colour", color);
    }
}
