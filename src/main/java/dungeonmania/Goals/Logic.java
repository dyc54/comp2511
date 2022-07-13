package dungeonmania.Goals;

public class Logic {

    private String type;
    private final String supergoal = "SUPERGOAL";
    private final String and = "AND";
    private final String or = "OR";

    public Logic(String type) {
        if (type.equals(and) || type.equals(or)) {
            this.type = type;
        } else {
            this.type = supergoal;
        }
    }
    /**
     * Return whether a and b have achieved with given logic
     * @param a
     * @param b
     * @return
     */
    private boolean hasAchieved(GoalsTree a, GoalsTree b) {
        switch (type) {
            case and:
                return a.hasAchieved() && b.hasAchieved();
            case or:
                return a.hasAchieved() || b.hasAchieved();
            default:
                return false;
        }

    }
    /**
     * Return wether given goal has achieved with given logic
     * @param goalNode
     * @return
     */
    public boolean hasAchieved(GoalsTree goalNode) {
        if (type.equals(supergoal)) {
            return goalNode.getGoal().hasAchieved();
        } else {
            return hasAchieved(goalNode.toGoalA(), goalNode.toGoalB());
        }
    }
    public String toString(GoalsTree node, boolean bracket) {
        if (type.equals(supergoal)) {
            return node.getGoal().toString();
        } else {

            String left = node.toGoalA().toString();
            String right = node.toGoalB().toString();
            String output;
            String format;
            if (left.equals("") || right.equals("")) {
                output = String.format("%s%s", left, right);
                format = "%s";
            } else {
                output = String.format("%s %s %s", node.toGoalA().toString(), toString(), node.toGoalB().toString());
                format = bracket? "(%s)": "%s";
            }
            return String.format(format, output);
        }
        
    }
    @Override
    public String toString() {
        if (!type.equals(supergoal)) {
            return type;
        }
        return "";
    }
}
