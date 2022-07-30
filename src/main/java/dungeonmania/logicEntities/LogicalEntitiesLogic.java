package dungeonmania.logicEntities;

import java.util.HashSet;

public abstract class LogicalEntitiesLogic {
    protected HashSet<LogicSubject> adjacentEntities;
    public LogicalEntitiesLogic(HashSet<LogicSubject> adjacentEntities) {
        this.adjacentEntities = adjacentEntities;
    }
    public abstract boolean isTrue();
    public abstract String logicType();
}
