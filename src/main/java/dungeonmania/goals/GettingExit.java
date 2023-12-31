package dungeonmania.goals;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class GettingExit implements GoalComponent{
    private Location playerLocation;
    private Collection<Entity> exits;
    public GettingExit() {
        playerLocation = new Location();
    }

    @Override
    public boolean hasAchieved() {
        // return true;
        return exits.stream().anyMatch(entity -> entity.getLocation().equals(playerLocation));
    }

    @Override
    public GoalComponent getMapData(DungeonMap map) {
        this.exits = map.getEntities("exit");
        return this;
    }

    @Override
    public GoalComponent getConfig(Config config) {
        return this;
    }
    
    @Override
    public String toString() {
        if (!hasAchieved()) {
            return new String(":exit");
        } else {
            return "";
        }
    }
    @Override
    public GoalComponent getPlayerData(Player player) {
        playerLocation = player.getLocation();
        return this;
    }
}
