package dungeonmania.collectableEntities;

import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;

public interface Useable {
    public void use(DungeonMap map, Player player);
}
