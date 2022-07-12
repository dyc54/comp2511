package dungeonmania.Goals;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class GettingExit implements Goal{
    private Location playerLocation;
    private Collection<Entity> exits;
    public GettingExit() {
        playerLocation = new Location();
    }
    @Override
    public boolean hasAchieved() {
        return exits.stream().anyMatch(entity -> entity.getLocation().equals(playerLocation));
    }

    @Override
    public Goal getMapData(DungeonMap map) {
        // TODO Auto-generated method stub
        this.exits = map.getEntities("exit");
        return this;
    }

    @Override
    public Goal getConfig(Config config) {
        // TODO Auto-generated method stub
        return this;
    }
    @Override
    public String toString() {
        return new String(":exit");
    }
}
