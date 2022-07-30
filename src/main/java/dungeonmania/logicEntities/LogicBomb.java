package dungeonmania.logicEntities;

import java.util.HashSet;

import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.Bomb;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.DungeonMapWirteDecorator;
import dungeonmania.helpers.Timer;

public class LogicBomb extends Bomb implements LogicObserver {
    private final HashSet<LogicSubject> subjects;
    private final LogicalEntitiesLogic logic;
    // private final DungeonMapWirteDecorator writer;
    public LogicBomb(String type, int x, int y, int bomb_radius, Timer timer, String logic, DungeonMapWirteDecorator decorator) {
        super(type, x, y, bomb_radius, decorator);
        subjects = new HashSet<>();
        this.logic = LogicalEntitiesFactroy.newLogic(logic, subjects, timer);
        // writer = decorator;
    }
    @Override
    public void active() {
        boom();
        
    }
    @Override
    public boolean isActivated() {
        return false;
    }
    @Override
    public void inactive() {
        
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
    public void use(DungeonMap map, Player player) {
        this.setLocation(player.getLocation());
        map.addEntity(this);
        map.getPlayer().removeInventoryList(this);
        if (logic.isTrue()) {
            active();
        } else {
            place();
        }
    }
    @Override
    public String getId() {
        return getEntityId();
    }
    @Override
    public void update(LogicSubject subject) {
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
        if (logic.isTrue() && hasPlaced()) {
            active();
        } 
        
    }
    @Override
    public void clear() {
        subjects.clear();
    }
    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject().put("logic", logic.logicType());
    }
}
