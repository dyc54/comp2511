package dungeonmania.helpers;
/**
 * Propose
 * to provide room for future expansion of similar structures, For example, nested buildable entities construction, etc.
 */
public class LogicCondition<T> {
    private final String type;
    private final static String superLogic = "SUPER";
    private final static String and = "AND";
    private final static String or = "OR";
    public LogicCondition(String type) {
        if (type.equals(and) || type.equals(or)) {
            this.type = type;
        } else {
            this.type = superLogic;
        }
    }

    public static <T> boolean compareTwo(String type, LogicContent<T> a, LogicContent<T> b) {
        switch (type) {
            case and:
                return a.isTrue() && b.isTrue();
            case or:
                return a.isTrue() || b.isTrue();
            default:
                return false;
        }
    }

    public boolean isTrue(LogicContent<T> content) {
        if (type.equals(superLogic)) {
            return content.isTrue();
        } else {
            return LogicCondition.compareTwo(type, content.getSubContentA(), content.getSubContentB());
        }
    }

    public String getLogic() {
        return type;
    }
    
    @Override
    public String toString() {
        return type;
    }

}
