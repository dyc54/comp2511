package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import dungeonmania.Entity;

public class BuildablePrerequisite {
    private List<Predicate<Entity>> condistions;
    private boolean satisfied;
    public BuildablePrerequisite() {
        condistions = new ArrayList<>();
        satisfied = false;
    }
    public BuildablePrerequisite attach(Predicate<Entity> condition) {
        condistions.add(condition);
        return this;
    }
    public boolean isSatisfied() {
        return satisfied;
    }
    /**
     * check whether there is an entity that meets all the conditions
     * @param entity
     * @return
     */
    public BuildablePrerequisite anyMatch(Iterator<Entity> entity) {
        if (condistions.size() == 0) {
            satisfied = true;
            return this;
        }
        while (entity.hasNext()) {
            Entity entity2 = entity.next();
            boolean match = condistions.stream().allMatch(predicate -> predicate.test(entity2));
            if (match) {
                satisfied = true;
                return this;
            }
        }
        satisfied = false;
        return this;
    }
    /**
     * check whether all entities that meets all the conditions
     * @param entity
     * @return
     */
    public BuildablePrerequisite allMatch(Iterator<Entity> entity) {
        if (condistions.size() == 0) {
            satisfied = true;
            return this;
        }
        while (entity.hasNext()) {
            Entity entity2 = entity.next();
            boolean match = condistions.stream().allMatch(predicate -> predicate.test(entity2));
            if (!match) {
                satisfied = false;
                return this;
            }
        }
        satisfied = true;
        return this;
    }
    
}
