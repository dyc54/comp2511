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

    /**
     * move the boulder to the same direction as player moves
     * If there is a switch on the current position, set the trigger of it to be
     * false
     * If there is a switch on the next position , set the trigger of it to be true
     * 
     * @param Position   p
     * @param DungeonMap map
     */
    public void moveBoulder(Position p, DungeonMap map) {
        super.setLocation(this.getLocation().getX() + p.getX(), this.getLocation().getY() + p.getY());
        // // If the boulder is currently on a switch
        // if (map.getEntities(this.getLocation().getX() - p.getX(),
        // this.getLocation().getY() - p.getY()).stream()
        // .anyMatch(e -> e.getType().equals("switch"))) {
        // Entity temp = map.getEntities(this.getLocation().getX() - p.getX(),
        // this.getLocation().getY() - p.getY())
        // .stream()
        // .filter(e -> e.getType().equals("switch"))
        // .findFirst().get();
        // }
        // // If the next position has an entity switch
        // if (map.getEntities(this.getLocation().getX(),
        // this.getLocation().getY()).stream()
        // .anyMatch(e -> e.getType().equals("switch"))) {
        // Entity temp = map.getEntities(this.getLocation().getX(),
        // this.getLocation().getY())
        // .stream()
        // .filter(e -> e.getType().equals("switch"))
        // .findFirst().get();
        // FloorSwitch floorSwitch = (FloorSwitch) temp;
        // floorSwitch.setTrigger(true);
        // }
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        if (entity instanceof Spider) {
            return false;
        }
        return false;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // !
        // System.out.println(String.format("entity is %s", entity.toString()));
        // System.out.println("interact with Boulder");
        if (entity instanceof Player) {
            Player player = (Player) entity;
            // if (player.getLocation().equals(getLocation())) {
            Position p = Location.getMoveDir(player.getPreviousLocation(), player.getLocation());
            Location next = getLocation().getLocation(p);
            // System.out.println(String.format("Dir %s -> %s", getLocation().toString(),
            // next.toString()));
            if (DungeonMap.isaccessible(map, next, this)) {
                // System.out.println(String.format("Blouder is moved %s -> %s", getLocation(),
                // next));
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
            // else {
            // // System.out.println(String.format("rtuen back to set %s",
            // player.getPreviousLocation().toString()));
            // System.out.println(String.format("Blouder: get back %s -> %s",
            // player.getLocation().toString(), player.getPreviousLocation().toString()));
            // player.setLocation(player.getPreviousLocation());
            // }
            // }
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
