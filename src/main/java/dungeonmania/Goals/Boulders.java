package dungeonmania.Goals;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;

public class Boulders implements Goal {
    Collection<Entity> switchs;
    @Override
    public boolean hasAchieved() {
        // TODO 
        // return switchs.stream().map(ele -> ele = (Entity) ele).anyMatch(switcher->switcher.);
        return false;
    }

    @Override
    public Goal getMapData(DungeonMap map) {
        switchs.addAll(map.getEntities("switch"));
        return this;
    }

    @Override
    public Goal getConfig(Config config) {
        // TODO Auto-generated method stub
        return this;
        
    }
    @Override
    public String toString() {
        if (hasAchieved()) {
            return new String(":boulders");
        } else {
            return "";
        }
    }
}
