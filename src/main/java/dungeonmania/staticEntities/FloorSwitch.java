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

public class FloorSwitch extends StaticEntity implements LogicSubject {
    private List<Bomb> bombs;
    private boolean trigger;
    private final HashSet<LogicObserver> observers;
    private LogicEntity temp;

    public FloorSwitch(String type, int x, int y) {
        super(type, x, y);
        this.trigger = false;
        this.bombs = new ArrayList<Bomb>();
        observers = new HashSet<>();
        temp = null;
    }
    public void setNotiSender(LogicEntity entity) {
        temp = entity;
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

    public void notifyAllBombs() {
        bombs.stream().forEach(e -> e.boom());
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return true;
    }

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        if (entity instanceof Boulder) {
            setTrigger(true);
            notifyAllBombs();
        } else {
            setTrigger(false);
        }
        notifyLogicObserver();
        return false;
    }

    @Override
    public void active() {
        setTrigger(true);
    }

    @Override
    public boolean isActivated() {
        return trigger;
    }

    @Override
    public void inactive() {
        setTrigger(false);
        
    }

    @Override
    public boolean equals(LogicEntity entity) {
        if (entity == null) {
            return false;
        }
        return Entity.equals(this, entity.getId());
    }

    @Override
    public void init(DungeonMap map) {
    }

    @Override
    public String getId() {
        return getEntityId();
    }

    @Override
    public void attach(LogicObserver observer) {
        if (!observer.equals(this)) {
            observers.add(observer);
            observer.update(this);
        }
    }

    @Override
    public void detach(LogicObserver observer) {
        observers.remove(observer);
        
    }

    @Override
    public void pull(LogicObserver observer) {
    }

    @Override
    public void notifyLogicObserver() {
        observers.stream().forEach(observer -> {
            if (!observer.equals(temp)) {
                observer.update(this);
            }
        });
        
    }



}
