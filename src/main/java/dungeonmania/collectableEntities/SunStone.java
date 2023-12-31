package dungeonmania.collectableEntities;

import dungeonmania.Player;
import dungeonmania.staticEntities.Door;

public class SunStone extends Treasure implements Openable{

    public SunStone(String type, int x, int y) {
        super(type, x, y);
    }

    @Override
    public boolean open(Door door, Player player) {
        door.open();
        return true;
    }
    
}
