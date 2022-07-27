package dungeonmania.logicEntities;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public interface LogicEntity {
    public void active();
    public boolean isActivated();
    public void inactive();
    public boolean equals(LogicEntity entity);
    public void init(DungeonMap map);
    public Location getLocation();
    public String getId();
}
