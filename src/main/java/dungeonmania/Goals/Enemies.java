package dungeonmania.Goals;

import dungeonmania.Player;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public class Enemies implements Goal {
    private int TargetNum;
    private int spawners;
    private int destroyed;
    public Enemies() {
        TargetNum = 0;
    }
    @Override
    public boolean hasAchieved() {
        // TODO Auto-generated method stub
        System.out.println(String.format("enemies %d/%d, spawner count: %d", destroyed, TargetNum, spawners));
        return destroyed >= TargetNum && spawners == 0;
    }

    @Override
    public Goal getMapData(DungeonMap map) {
        // TODO Auto-generated method stub
        destroyed = map.getDestoriedCounter();
        spawners = map.getEntities("zombie_toast_spawner").size();
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
        if (!hasAchieved()) {
            return new String(":enemies");
        } else {
            return "";
        }
    }
    @Override
    public Goal getMapData(Player player) {
        // TODO Auto-generated method stub
        return null;
    }
}
