package dungeonmania.helpers;
/**
 * Propose
 * to provide room for future expansion of similar structures, For example, nested buildable entities construction, etc.
 */
public interface LogicContent<T> {
    public T getContent();
    public LogicContent<T> getSubContentA();
    public LogicContent<T> getSubContentB();
    public boolean isTrue();
}
