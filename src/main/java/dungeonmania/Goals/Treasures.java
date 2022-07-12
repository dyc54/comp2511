package dungeonmania.Goals;

import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public class Treasures implements Goal{
    private int TargetNum;
    private int GoldNum;
    public Treasures() {
        TargetNum = 0;
        GoldNum = 0;
    }
    @Override
    public boolean hasAchieved() {
        // TODO Auto-generated method stub
        return true;
        // return GoldNum >= TargetNum;
    }

    @Override
    public Goal getMapData(DungeonMap map) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Goal getConfig(Config config) {
        // TODO Auto-generated method stub
        TargetNum = config.treasure_goal;
        return this;
    }
    @Override
    public String toString() {
        if (hasAchieved()) {
            return new String(":treasure");
        } else {
            return "";
        }
    }
}
