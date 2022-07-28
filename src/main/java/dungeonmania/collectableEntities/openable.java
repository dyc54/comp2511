package dungeonmania.collectableEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.staticEntities.Door;

public interface openable {
    public boolean open(Door door, Player player);
}
