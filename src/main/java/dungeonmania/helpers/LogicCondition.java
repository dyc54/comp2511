package dungeonmania.helpers;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.text.AbstractDocument.Content;

public class LogicCondition<T> {
    private final String type;
    private final String superLogic = "SUPER";
    private final String and = "AND";
    private final String or = "OR";
    public LogicCondition(String type) {
        if (type.equals(and) || type.equals(or)) {
            this.type = type;
        } else {
            this.type = superLogic;
        }
    }
    private boolean compareTwo(LogicContent<T> a, LogicContent<T> b) {
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
            return compareTwo(content.getSubContentA(), content.getSubContentB());
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
