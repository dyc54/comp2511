package dungeonmania.Goals;

import java.util.Collection;
import java.util.LinkedList;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.StaticEntities.FloorSwitch;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public class Boulders implements GoalComponent {
    Collection<Entity> switchs;
    public Boulders() {
        switchs = new LinkedList<>();
    }
    @Override
    public boolean hasAchieved() {
        // TODO 
        // return true;
        return switchs.stream().map(ele ->  {return (FloorSwitch) ele;}).allMatch(switcher->switcher.getTrigger());
        // return false;
    }

    @Override
    public GoalComponent getMapData(DungeonMap map) {
        switchs.addAll(map.getEntities("switch"));
        return this;
    }

    @Override
    public GoalComponent getConfig(Config config) {
        // TODO Auto-generated method stub
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
    public GoalComponent getMapData(Player player) {
        // TODO Auto-generated method stub
        return this;
    }
}
