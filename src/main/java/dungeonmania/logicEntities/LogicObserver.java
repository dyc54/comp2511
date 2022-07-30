package dungeonmania.logicEntities;

public interface LogicObserver extends LogicEntity {
    public void update(LogicSubject subject);
    public void clear();
}
