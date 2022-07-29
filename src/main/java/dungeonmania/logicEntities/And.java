package dungeonmania.logicEntities;

import java.util.HashSet;

public class And extends LogicalEntitiesLogic{

    public And(HashSet<LogicSubject> adjacentEntities) {
        super(adjacentEntities);
    }

    @Override
    public boolean isTrue() {
        if (adjacentEntities.size() < 2) {
            return false;
        }
        return adjacentEntities.stream().allMatch(entity -> entity.isActivated());
    }
    @Override
    public String logicType() {
        return "and";
    }
}
