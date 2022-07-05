package dungeonmania.StaticEntities;

import dungeonmania.util.Direction;

public class Boulder extends StaticEntity {
    public Boulder(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
    }

    /**
     * move the boulder to the same direction as player moves
     * 
     * @param Direction
     */
    public void move(Direction direction) {

    }
}
