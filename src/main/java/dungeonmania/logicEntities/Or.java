package dungeonmania.logicEntities;

import java.util.HashSet;

public class Or extends LogicalEntitiesLogic{

    public Or(HashSet<LogicSubject> adjacentEntities) {
        super(adjacentEntities);
    }

    @Override
    public boolean isTrue() {
        // TODO Auto-generated method stub
        if (adjacentEntities.size() < 1) {
            return false;
        }
        return adjacentEntities.stream().anyMatch(entity -> entity.isActivated());
    }
    
}
