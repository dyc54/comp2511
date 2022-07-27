package dungeonmania.logicEntities;

import java.util.HashSet;

import dungeonmania.Entity;
import dungeonmania.collectableEntities.Bomb;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.staticEntities.Boulder;
import dungeonmania.staticEntities.FloorSwitch;

public class LogicalSwitch extends FloorSwitch implements LogicSubject {
    private final HashSet<LogicObserver> observers;
    private DungeonMap map;
    private LogicalEntitiesLogic logic;
    public LogicalSwitch(String type, int x, int y, String logic) {
        super(type, x, y);
        observers = new HashSet<>();
        // this.logic = LogicalEntitiesFactroy.newLogic(logic, subjects);

    }
    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        if (entity instanceof Boulder) {
            active();
        } else {
            inactive();
        }
        System.out.println(String.format("%s become active", getEntityId()));
        notifyLogicObserver();
        return false;
    }
    
    @Override
    public void active() {
        setTrigger(true);
    }

    @Override
    public boolean isActivated() {
        return getTrigger();
    }

    @Override
    public void inactive() {
        setTrigger(false);
    }

    @Override
    public boolean equals(LogicEntity entity) {
        return Entity.equals(this, entity.getId());
    }

    @Override
    public void init(DungeonMap map) {
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
        observers.stream().forEach(observer -> observer.update(this));
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return getEntityId();
    }
}
