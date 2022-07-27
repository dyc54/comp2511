package dungeonmania.logicEntities;

import java.util.HashSet;

public abstract class LogicalEntitiesLogic {
    // public LogicalEntitiesLogic()
    protected HashSet<LogicSubject> adjacentEntities;
    public LogicalEntitiesLogic(HashSet<LogicSubject> adjacentEntities) {
        this.adjacentEntities = adjacentEntities;
    }
    public abstract boolean isTrue();
}
