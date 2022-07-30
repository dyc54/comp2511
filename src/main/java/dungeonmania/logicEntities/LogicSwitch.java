package dungeonmania.logicEntities;

import java.util.HashSet;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Timer;
import dungeonmania.staticEntities.FloorSwitch;

public class LogicSwitch extends FloorSwitch implements LogicObserver {
    private final HashSet<LogicSubject> subjects;
    private final LogicalEntitiesLogic logic;

    public LogicSwitch(String type, int x, int y, Timer timer, String logic) {
        super(type, x, y);
        subjects = new HashSet<>();
        this.logic = LogicalEntitiesFactroy.newLogic(logic, subjects, timer);
    }

    @Override
    public void update(LogicSubject subject) {
        // TODO Auto-generated method stub
        if (!subjects.contains(subject)) {
            subjects.add(subject);
        } else {
            if (logic.isTrue()) {
                active();
                notifyAllBombs();
            } else {
                inactive();
            }
            setNotiSender(subject);
            notifyLogicObserver();
        }
    }

    @Override
    public void clear() {
        subjects.clear();
    }
    @Override
    public void init(DungeonMap map) {
        // TODO Auto-generated method stub
        LogicalEntitiesFactroy.init(this, map);
    }
    
}
