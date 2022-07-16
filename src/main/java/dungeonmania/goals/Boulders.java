package dungeonmania.goals;

import java.util.Collection;
import java.util.LinkedList;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.staticEntities.FloorSwitch;

public class Boulders implements GoalComponent {
    Collection<Entity> switchs;
    public Boulders() {
        switchs = new LinkedList<>();
    }
    @Override
    public boolean hasAchieved() {
        return switchs.stream().map(ele ->  {return (FloorSwitch) ele;}).allMatch(switcher->switcher.getTrigger());
    }

    @Override
    public GoalComponent getMapData(DungeonMap map) {
        switchs.addAll(map.getEntities("switch"));
        return this;
    }

    @Override
    public GoalComponent getConfig(Config config) {
        return this;
        
    }
    @Override
    public String toString() {
        if (!hasAchieved()) {
            return new String(":boulders");
        } else {
            return "";
        }
    }

    @Override
    public GoalComponent getPlayerData(Player player) {
        return this;
    }
}
