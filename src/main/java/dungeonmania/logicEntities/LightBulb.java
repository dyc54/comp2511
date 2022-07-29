package dungeonmania.logicEntities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.security.auth.Subject;

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
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void active() {
        setType("light_bulb_on");        
    }

    @Override
    public boolean isActivated() {
        // TODO Auto-generated method stub
        return getType().equals("light_bulb_on");
    }

    @Override
    public void inactive() {
        // TODO Auto-generated method stub
        setType("light_bulb_off");        
    }

    @Override
    public void update(LogicSubject subject) {
        // TODO Auto-generated method stub
        System.out.println("bulb was notified (update)");
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
        if (logic.isTrue()) {
            active();
            System.out.println("bulb active");
        } else {
            inactive();
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
    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject().put("logic", logic.logicType());
    }
    
}
