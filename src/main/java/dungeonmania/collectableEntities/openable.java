package dungeonmania.collectableEntities;

import dungeonmania.Player;
import dungeonmania.staticEntities.Door;

public interface openable {
    public boolean open(Door door, Player player);
}
