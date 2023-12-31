package dungeonmania.logicEntities;

import java.util.HashSet;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Timer;
import dungeonmania.staticEntities.Door;

public class SwitchDoor extends Door implements LogicObserver{
    private final HashSet<LogicSubject> subjects;
    private final LogicalEntitiesLogic logic;
    public SwitchDoor(String type, int x, int y, int key, String logic, Timer timer) {
        super(type, x, y, key);
        subjects = new HashSet<>();
        this.logic = LogicalEntitiesFactroy.newLogic(logic, subjects, timer);
    }

    @Override
    public void active() {
        super.open();
    }

    @Override
    public boolean isActivated() {
        return super.isOpened();
    }

    @Override
    public void inactive() {
        super.close();
    }

    @Override
    public void update(LogicSubject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
        if (logic.isTrue()) {
            active();
        } else {
            inactive();
        }
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
        LogicalEntitiesFactroy.init(this, map);
        
        
    }
    @Override
    public void clear() {
        subjects.clear();
        
    }
    @Override
    public String getId() {
        return getEntityId();
    }
    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject().put("logic", logic.logicType());
    }
}
