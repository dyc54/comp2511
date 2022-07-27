package dungeonmania.logicEntities;

import java.util.HashSet;

public class Xor extends LogicalEntitiesLogic{

    public Xor(HashSet<LogicSubject> adjacentEntities) {
        super(adjacentEntities);
    }

    @Override
    public boolean isTrue() {
        if (adjacentEntities.size() != 1) {
            return false;
        }
        return adjacentEntities.stream().findFirst().get().isActivated();
    }
    
}
