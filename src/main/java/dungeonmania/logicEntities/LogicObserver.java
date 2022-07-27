package dungeonmania.logicEntities;

public interface LogicObserver extends LogicEntity {
    public void update(LogicSubject subject);
    public void clear();
    // public void add(LogicSubject sub);
    // public
    // public void push()
}
