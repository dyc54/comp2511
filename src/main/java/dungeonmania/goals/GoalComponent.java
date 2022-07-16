package dungeonmania.goals;

import dungeonmania.Player;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public interface GoalComponent {
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
    public GoalComponent getMapData(DungeonMap map);
    
    public GoalComponent getPlayerData(Player player);
    
    /**
     * Load config from congis to goal
     * @param config
     * @return
     */
    public GoalComponent getConfig(Config config);


}
