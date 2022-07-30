package dungeonmania.goals;

import java.util.function.Consumer;

import dungeonmania.helpers.LogicCondition;
import dungeonmania.helpers.LogicContent;

public class GoalsTree implements LogicContent<GoalComponent> {
    private GoalsTree left;
    private GoalsTree right;
    private LogicCondition<GoalComponent> condition;
    private GoalComponent goal;
    public GoalsTree(String type, GoalComponent goal) {
        condition = new LogicCondition<>(type);
        this.goal = goal;
        left = null;
        right = null;
    }

    public GoalsTree(String type) {
        condition = new LogicCondition<>(type);
        goal = null;
        left = null;
        right = null;
    }

    /**
     * Return one of its subgoal
     * @return
     */
    public GoalsTree toGoalA() {
        return left;
    }

    /**
     * Return another subgoal
     * @return
     */
    public GoalsTree toGoalB() {
        return right;
    }

    /**
     * Return whether the node of tree has achieved
     * @return
     */
    public boolean hasAchieved() {
        if (left == null || right == null) {
            return goal.hasAchieved();
        } else {
            return condition.isTrue(this);
        }
    }

    /**
     * get the Goal of given tree node
     * @return
     */
    public GoalComponent getGoal() {
        return goal;
    }

    /**
     * Attach given goal to tree's subgoal
     * @param goal
     */
    public void attachGoalA(GoalsTree goal) {
        this.left = goal;
    }

    /**
     * Attach given goal to another tree's subgoal
     * @param goal
     */
    public void attachGoalB(GoalsTree goal) {
        this.right = goal;
    }

    /**
     * Return a new GoalTree node
     * @param type
     * @param goal
     * @return
     */
    public static GoalsTree AsGoalsTreeNode(String type, GoalComponent goal) {
        return new GoalsTree(type, goal);
    }

    /**
     * Set the goal of given tree node.
     * @param goal
     */
    public void setGoal(GoalComponent goal) {
        this.goal = goal;
    }

    /**
     * Map a function for all Goal 
     * @param func
     * @return
     */
    public GoalsTree mapForAll(Consumer<GoalComponent> func) {
        if (left != null) {
            left.mapForAll(func);
        }
        if (goal != null) {
            func.accept(goal);
        }
        if (right != null) {
            right.mapForAll(func);
        }
        return this;
    }

    public String toStringAtRoot() {
        if (hasAchieved()) {
            return "";
        }
        if (left == null || right == null) {
            return goal.toString();
        } else {
            return toString(false);
        }
    }

    public String toString(boolean bracket) {
        String left = toGoalA().toString();
        String right = toGoalB().toString();
        String output;
        String format;
        if (left.equals("") || right.equals("")) {
            output = String.format("%s%s", left, right);
            format = "%s";
        } else {
            output = String.format("%s %s %s", toGoalA().toString(), condition.toString(), toGoalB().toString());
            format = bracket? "(%s)": "%s";
        }
        return String.format(format, output);

    }

    @Override
    public String toString() {
        if (hasAchieved()) {
            return "";
        }
        if (left == null || right == null) {
            return goal.toString();
        } else {
            return toString(true);
        }
    }

    @Override
    public GoalComponent getContent() {
        return getGoal();
    }
    
    @Override
    public boolean isTrue() {
        return hasAchieved();
    }

    @Override
    public LogicContent<GoalComponent> getSubContentA() {
        return toGoalA();
    }
    
    @Override
    public LogicContent<GoalComponent> getSubContentB() {
        return toGoalB();
    }
    
}
