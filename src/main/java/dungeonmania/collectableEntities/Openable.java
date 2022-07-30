package dungeonmania.collectableEntities;

import dungeonmania.Player;
import dungeonmania.staticEntities.Door;

public interface Openable {
    public boolean open(Door door, Player player);
}
