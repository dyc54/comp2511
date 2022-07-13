package dungeonmania.StaticEntities;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.MovingEntities.Mercenary;
import dungeonmania.MovingEntities.ZombieToast;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.util.Position;

public class Portal extends StaticEntity {
    final String color;

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
            Location target = map.getEntities("portal")
                                .stream()
                                .filter(portal -> ((Portal) portal).getColour().equals(color) && !portal.equals(this))
                                .collect(Collectors.toList()).get(0).getLocation();

                System.out.println(String.format("portal: tp %s ->  %s", entity.getLocation().toString(), target.toString()));
                
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
                // if (DungeonMap.isaccessible(map, target, entity) && ) {
            //     System.out.println("DEST is accessible");
            //     List<Function<Location, Location>> possible = target.getFourNearPosition().stream().filter(func -> DungeonMap.isaccessible(map, func.apply(target), entity)).collect(Collectors.toList());
            //     if (possible.size() != 0) {
            //         // Location next = possible.get(0).apply(target);
            //         System.out.println("SET");
            //         System.out.println(next.toString());
            //         entity.setLocation(next);
            //     } 
            // }
        }
        return hasChain;
    }
    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap  map) {
        // do nothing by defalut 
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }
}
