package dungeonmania.helpers;

public interface LogicContent<T> {
    public T getContent();
    public LogicContent<T> getSubContentA();
    public LogicContent<T> getSubContentB();
    public boolean isTrue();
}
