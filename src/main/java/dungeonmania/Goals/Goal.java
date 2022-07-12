package dungeonmania.Goals;

import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public interface Goal {
    /**
     * Return whether a goal has achieved
     * @return
     */
    public boolean hasAchieved();
    /**
     * Load the data from map to goal
     * @param map
     * @return
     */
    public Goal getMapData(DungeonMap map);
    // TODO: waiting player
    // public Goal getMapData(Player player);
    
    /**
     * Load config from congis to goal
     * @param config
     * @return
     */
    public Goal getConfig(Config config);


}
