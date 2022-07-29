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
        // TODO Auto-generated method stub
        boom();
        
    }
    @Override
    public boolean isActivated() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public void inactive() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public boolean equals(LogicEntity entity) {
        // TODO Auto-generated method stub
        if (entity == null) {
            return false;
        }
        return Entity.equals(this, entity.getId());
    }
    @Override
    public void init(DungeonMap map) {
        // TODO Auto-generated method stub
        LogicalEntitiesFactroy.init(this, map);
        
    }
    
    @Override
    public void use(DungeonMap map, Player player) {
        // this.setLocation(player.getLocation());
        // this.hasPlaced = true;
        // map.addEntity(this);
        // map.getPlayer().removeInventoryList(this);
        // map.getFourNearEntities(player.getLocation()).stream().forEach(e -> {
        //     if (e instanceof FloorSwitch) {
        //         FloorSwitch floorSwitch = (FloorSwitch) e;
        //         if (floorSwitch.getTrigger()) {
        //             this.update(map);
        //         } else {
        //             floorSwitch.bombAttach(this);
        //         }
        //     }
        // });
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
        // TODO Auto-generated method stub
        return getEntityId();
    }
    @Override
    public void update(LogicSubject subject) {
        // TODO Auto-generated method stub
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        }
        System.out.println("bomb was notified");
        if (logic.isTrue() && hasPlaced()) {
            active();
        } 
        
    }
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        subjects.clear();
    }
    @Override
    public JSONObject toJSONObject() {
        return super.toJSONObject().put("logic", logic.logicType());
    }
}
