package dungeonmania.StaticEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class FloorSwitch extends StaticEntity {

    private boolean trigger;

    public FloorSwitch(String type, int x, int y) {
        super(type, x, y);
        this.trigger = false;
    }

    public boolean getTrigger() {
        return this.trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    /**
     * Return true if boulder's location is equal to trigger's location
     * 
     * @param Location
     * @return boolean
     */
    public boolean boulderIsOnSwitch(Location location) {
        if (this.getLocation().equals(location)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean interact(Entity entity, DungeonMap map) {
       if (entity instanceof Boulder) {
            setTrigger(true);
       }
       return false;
    }
    
}
