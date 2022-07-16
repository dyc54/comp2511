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
        // TODO Auto-generated method stub
        System.out.println(String.format("Gold %d/%d", GoldNum, TargetNum));
        return GoldNum >= TargetNum;
    }

    @Override
    public GoalComponent getMapData(DungeonMap map) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public GoalComponent getConfig(Config config) {
        // TODO Auto-generated method stub
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
    public GoalComponent getMapData(Player player) {
        // TODO Auto-generated method stub
        GoldNum = player.getInventory().getItems("treasure").size();
        return this;
    }
}
