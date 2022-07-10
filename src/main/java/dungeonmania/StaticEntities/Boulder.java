package dungeonmania.StaticEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Boulder extends StaticEntity {
    public Boulder(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
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
        // If the boulder is currently on a switch
        if (map.getEntities(this.getLocation().getX() - p.getX(), this.getLocation().getY() - p.getY()).stream()
                .anyMatch(e -> e.getType().equals("switch"))) {
            Entity temp = map.getEntities(this.getLocation().getX() - p.getX(), this.getLocation().getY() - p.getY())
                    .stream()
                    .filter(e -> e.getType().equals("switch"))
                    .findFirst().get();
            FloorSwitch floorSwitch = (FloorSwitch) temp;
            floorSwitch.setTrigger(false);
        }
        // If the next position has an entity switch
        if (map.getEntities(this.getLocation().getX(), this.getLocation().getY()).stream()
                .anyMatch(e -> e.getType().equals("switch"))) {
            Entity temp = map.getEntities(this.getLocation().getX(), this.getLocation().getY())
                    .stream()
                    .filter(e -> e.getType().equals("switch"))
                    .findFirst().get();
            FloorSwitch floorSwitch = (FloorSwitch) temp;
            floorSwitch.setTrigger(true);
        }
    }

}
