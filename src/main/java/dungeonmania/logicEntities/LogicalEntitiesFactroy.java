package dungeonmania.logicEntities;

import java.util.HashSet;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Timer;

public class LogicalEntitiesFactroy {
    public static void init(LogicObserver entity, DungeonMap map) {
        entity.clear();
        map.getFourNearEntities(entity.getLocation())
            .stream()
            .filter(mapEntity -> (mapEntity instanceof LogicSubject))
            .map(mapEntity -> (LogicSubject) mapEntity)
            .forEach(subject -> {
                subject.attach(entity);
            }
        );
        
    }
    public static LogicalEntitiesLogic newLogic(String str, HashSet<LogicSubject> adjacentEntities, Timer timer) {
        switch (str.toUpperCase()) {
            case "AND":
                return new And(adjacentEntities);
            case "OR":
                return new Or(adjacentEntities);
            case "XOR":
                return new Xor(adjacentEntities);
            case "CO_AND":
                return new CoAnd(adjacentEntities, timer);
            default:
                break;
        }
        return null;
    }
    
    
}
