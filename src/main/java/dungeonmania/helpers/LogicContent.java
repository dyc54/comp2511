package dungeonmania.helpers;

import java.util.List;

public interface LogicContent<T> {
    public T getContent();
    public LogicContent<T> getSubContentA();
    public LogicContent<T> getSubContentB();
    // public LogicContent<T> getSubLogic(int index);
    // public List<LogicContent<T>> getSubLogics();
    // public int getSubLogicCount();
    public boolean isTrue();
}
