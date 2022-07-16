package dungeonmania.helpers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.text.AbstractDocument.Content;

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
        boolean condA = a == null ? false: a.isTrue();
        boolean condB = b == null ? false: b.isTrue();
        switch (type) {
            case and:
                return condA && condB;
            case or:
                return condA || condB;
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
