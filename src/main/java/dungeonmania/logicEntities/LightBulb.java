package dungeonmania.logicEntities;

import java.util.HashSet;
import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Timer;
import dungeonmania.staticEntities.StaticEntity;

public class LightBulb extends StaticEntity implements LogicObserver{
    private final HashSet<LogicSubject> subjects;
    private final LogicalEntitiesLogic logic;
    public LightBulb(String type, int x, int y, String logic, Timer timer) {
        super(type, x, y);
        subjects = new HashSet<>();
        this.logic = LogicalEntitiesFactroy.newLogic(logic, subjects, timer);
    }

    public void addSubject(LogicSubject subject)  {
        subjects.add(subject);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return true;
    }

    @Override
    public void active() {
        setType("light_bulb_on");        
    }

    @Override
    public boolean isActivated() {
        return getType().equals("light_bulb_on");
    }

    @Override
    public void inactive() {
        setType("light_bulb_off");        
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
