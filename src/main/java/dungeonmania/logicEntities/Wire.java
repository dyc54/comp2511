package dungeonmania.logicEntities;

import java.util.HashSet;

import dungeonmania.Entity;
import dungeonmania.collectableEntities.Bomb;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.staticEntities.StaticEntity;

public class Wire extends StaticEntity implements LogicSubject, LogicObserver{
    private boolean activated;
    private final HashSet<LogicObserver> observers;
    private final HashSet<LogicSubject> subjects;
    private final LogicalEntitiesLogic logic;
    private LogicEntity temp;
    public Wire(String type, int x, int y, String logic) {
        super(type, x, y);
        activated = false;
        observers = new HashSet<>();
        subjects = new HashSet<>();
        this.logic = LogicalEntitiesFactroy.newLogic(logic, subjects);
        temp = null;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return true;
    }

    @Override
    public void active() {
        activated = true;
    }

    @Override
    public boolean isActivated() {
        return activated;
    }

    @Override
    public void inactive() {
        activated = false;
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
    public void notifyLogicObserver() {
        observers.stream().forEach(observer -> {
            if (!observer.equals(temp)) {
                observer.update(this);
            }
        });
        
    }
    @Override
    public void update(LogicSubject subject) {
        
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        } else {
            if (logic.isTrue()) {
                active();
            } else {
                inactive();
            }
            temp = subject;
            notifyLogicObserver();
        }
        
    }

    @Override
    public void pull(LogicObserver observer) {
        // * Pass observer but
        subjects.stream().forEach(subject -> subject.pull(observer));
        if (observer instanceof Bomb)  {
            Bomb bomb = (Bomb) observer;
            if (bomb.getLocation().distance(getLocation()) <= bomb.getBomb_radius()) {
                subjects.stream().forEach(subject -> subject.detach(this));
            }
        }
        
    }
    @Override
    public boolean equals(LogicEntity entity) {
        return Entity.equals(this, entity.getId());
    }

    @Override
    public void init(DungeonMap map) {
        LogicalEntitiesFactroy.init(this, map);
    }
    @Override
    public void clear() {
        subjects.clear();
        
    }
    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return getEntityId();
    }
}
