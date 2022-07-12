package dungeonmania.Goals;

import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public class Enemies implements Goal {
    private int TargetNum;
    private boolean hasspawners;
    public Enemies() {
        TargetNum = 0;
    }
    @Override
    public boolean hasAchieved() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Goal getMapData(DungeonMap map) {
        // TODO Auto-generated method stub
        return this;
    }

    @Override
    public Goal getConfig(Config config) {
        // TODO Auto-generated method stub
        TargetNum = config.enemy_goal;
        return this;
    }
    @Override
    public String toString() {
        if (hasAchieved()) {
            return new String(":enemies");
        } else {
            return "";
        }
    }
}
