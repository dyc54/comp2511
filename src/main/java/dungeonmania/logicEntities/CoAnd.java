package dungeonmania.logicEntities;

import java.util.HashSet;

import dungeonmania.helpers.Timer;

public class CoAnd extends LogicalEntitiesLogic{
    Timer time;
    private int tick;
    private long actived_num;
    private long prev;
    public CoAnd(HashSet<LogicSubject> adjacentEntities, Timer time) {
        super(adjacentEntities);
        this.time = time;
        tick = 0;
        actived_num = 0;
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
            prev = actived_num;
            actived_num = adjacentEntities.stream().filter(entity -> entity.isActivated()).count();
            count = actived_num - prev;
        } else {
            long temp = adjacentEntities.stream().filter(entity -> entity.isActivated()).count();
            actived_num = temp > actived_num ? temp : actived_num;
            count = actived_num - prev;
            
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
