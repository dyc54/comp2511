package dungeonmania.logicEntities;

public interface LogicSubject extends LogicEntity{
    public void attach(LogicObserver observer);
    public void detach(LogicObserver observer);
    public void pull(LogicObserver observer);
    public void notifyLogicObserver();
}
