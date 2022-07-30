package dungeonmania.goals;

import dungeonmania.Player;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public class Enemies implements GoalComponent {
    private int TargetNum;
    private int spawners;
    private int destroyed;
    public Enemies() {
        TargetNum = 0;
    }
    @Override
    public boolean hasAchieved() {
        System.out.println(String.format("enemies %d/%d, spawner count: %d", destroyed, TargetNum, spawners));
        return destroyed >= TargetNum && spawners == 0;
    }

    @Override
    public GoalComponent getMapData(DungeonMap map) {
        destroyed = map.getDestoriedCounter();
        spawners = map.getEntities("zombie_toast_spawner").size();
        return this;
    }

    @Override
    public GoalComponent getConfig(Config config) {
        TargetNum = config.enemyGoal;
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
    public GoalComponent getPlayerData(Player player) {
        return null;
    }
}
