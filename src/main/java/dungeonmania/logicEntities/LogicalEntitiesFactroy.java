package dungeonmania.logicEntities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import dungeonmania.helpers.DungeonMap;

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
    public static LogicalEntitiesLogic newLogic(String str, HashSet<LogicSubject> adjacentEntities) {
        switch (str.toUpperCase()) {
            case "AND":
                return new And(adjacentEntities);
            case "OR":
                return new Or(adjacentEntities);
            case "XOR":
                return new Xor(adjacentEntities);
            case "CO_AND":
                return new And(adjacentEntities);
            default:
                break;
        }
        return null;
    }
    
    
}
