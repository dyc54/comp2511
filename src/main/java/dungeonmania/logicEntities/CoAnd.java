package dungeonmania.logicEntities;

import java.util.HashSet;

public class CoAnd extends LogicalEntitiesLogic{
    int count;
    public CoAnd(HashSet<LogicSubject> adjacentEntities) {
        super(adjacentEntities);
        count = 0;
    }

    @Override
    public boolean isTrue() {
        System.out.println(String.format("Co_and %d", count));
        if (adjacentEntities.size() < 2) {
            return false;
        }
        return false;
    }
    
}
