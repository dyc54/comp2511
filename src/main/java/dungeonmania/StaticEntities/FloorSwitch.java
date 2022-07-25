package dungeonmania.StaticEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.CollectableEntities.Bomb;
import dungeonmania.helpers.DungeonMap;

public class FloorSwitch extends StaticEntity {

    private List<Bomb> bombs;
    private boolean trigger;

    public FloorSwitch(String type, int x, int y) {
        super(type, x, y);
        this.trigger = false;
        this.bombs = new ArrayList<Bomb>();
    }

    public boolean getTrigger() {
        return this.trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    public void bombAttach(Bomb bomb) {
        bombs.add(bomb);
    }

    public void notifyAllBombs(DungeonMap map) {
        bombs.stream().forEach(e -> e.update(map));
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return true;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        if (entity instanceof Boulder) {
            setTrigger(true);
            notifyAllBombs(map);
        } else {
            setTrigger(false);
        }
        return false;
    }

}
