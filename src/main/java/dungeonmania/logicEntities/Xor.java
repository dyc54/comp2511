package dungeonmania.logicEntities;

import java.util.HashSet;

public class Xor extends LogicalEntitiesLogic{

    public Xor(HashSet<LogicSubject> adjacentEntities) {
        super(adjacentEntities);
    }

    @Override
    public boolean isTrue() {
        if (adjacentEntities.size() == 0) {
            return false;
        }
        return adjacentEntities.stream().filter(entity -> entity.isActivated()).count() == 1;
    }
    @Override
    public String logicType() {
        return "xor";
    }
    
}
