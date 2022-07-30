package dungeonmania.goals;

import dungeonmania.Player;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public class Treasures implements GoalComponent{
    private int TargetNum;
    private int GoldNum;
    public Treasures() {
        TargetNum = 0;
        GoldNum = 0;
    }

    @Override
    public boolean hasAchieved() {
        return GoldNum >= TargetNum;
    }

    @Override
    public GoalComponent getMapData(DungeonMap map) {
        return this;
    }

    @Override
    public GoalComponent getConfig(Config config) {
        TargetNum = config.treasure_goal;
        return this;
    }

    @Override
    public String toString() {
        if (!hasAchieved()) {
            return new String(":treasure");
        } else {
            return "";
        }
    }

    @Override
    public GoalComponent getPlayerData(Player player) {
        GoldNum = player.getInventory().getItems("treasure").size() + player.getInventory().getItems("sun_stone").size();
        return this;
    }
}
