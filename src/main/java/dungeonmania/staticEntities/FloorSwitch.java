package dungeonmania.staticEntities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.collectableEntities.Bomb;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.logicEntities.LogicEntity;
import dungeonmania.logicEntities.LogicObserver;
import dungeonmania.logicEntities.LogicSubject;

public class FloorSwitch extends StaticEntity implements LogicSubject  {
    private List<Bomb> bombs;
    private boolean trigger;
    private final HashSet<LogicObserver> observers;

    public FloorSwitch(String type, int x, int y) {
        super(type, x, y);
        this.trigger = false;
        this.bombs = new ArrayList<Bomb>();
        observers = new HashSet<>();
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
        notifyLogicObserver();
        return false;
    }

    @Override
    public void active() {
        // TODO Auto-generated method stub
        setTrigger(true);
    }

    @Override
    public boolean isActivated() {
        // TODO Auto-generated method stub
        return trigger;
    }

    @Override
    public void inactive() {
        // TODO Auto-generated method stub
        setTrigger(false);
        
    }

    @Override
    public boolean equals(LogicEntity entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void init(DungeonMap map) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return getEntityId();
    }

    @Override
    public void attach(LogicObserver observer) {
        // TODO Auto-generated method stub
        if (!observer.equals(this)) {
            observers.add(observer);
            observer.update(this);
        }
    }

    @Override
    public void detach(LogicObserver observer) {
        // TODO Auto-generated method stub
        observers.remove(observer);
        
    }

    @Override
    public void pull(LogicObserver observer) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void notifyLogicObserver() {
        // TODO Auto-generated method stub
        observers.stream().forEach(observer -> observer.update(this));
        
    }



}
