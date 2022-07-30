package dungeonmania.logicEntities;

import java.util.HashSet;

import dungeonmania.helpers.Timer;

public class CoAnd extends LogicalEntitiesLogic{
    Timer time;
    private int tick;
    private long activedNum;
    private long prev;
    public CoAnd(HashSet<LogicSubject> adjacentEntities, Timer time) {
        super(adjacentEntities);
        this.time = time;
        tick = 0;
        activedNum = 0;
        prev = 0;
    }

    @Override
    public boolean isTrue() {
        if (time == null) {
            return adjacentEntities.stream().filter(entity -> entity.isActivated()).count() >= 2;
        }
        long count = 0;
        if (time.getTime() != tick) {
            tick = time.getTime();
            prev = activedNum;
            activedNum = adjacentEntities.stream().filter(entity -> entity.isActivated()).count();
            count = activedNum - prev;
        } else {
            long temp = adjacentEntities.stream().filter(entity -> entity.isActivated()).count();
            activedNum = temp > activedNum ? temp : activedNum;
            count = activedNum - prev;
        }
        if (count >= 2) {
            return true;
        }
        return false;
    }
    @Override
    public String logicType() {
        return "co_and";
    }
}
