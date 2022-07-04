package dungeonmania.Goals;

import java.util.function.Consumer;

public class GoalsTree {
    GoalsTree left;
    GoalsTree right;
    Logic logic;
    Goal goal;
    public GoalsTree(String type, Goal goal) {
        logic = new Logic(type);
        this.goal = goal;
        left = null;
        right = null;
    }
    public GoalsTree(String type) {
        logic = new Logic(type);
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
            return logic.hasAchieved(this);
        }
    }
    /**
     * get the Goal of given tree node
     * @return
     */
    public Goal getGoal() {
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
    public static GoalsTree AsGoalsTreeNode(String type, Goal goal) {
        return new GoalsTree(type, goal);
    }
    /**
     * Set the goal of given tree node.
     * @param goal
     */
    public void setGoal(Goal goal) {
        this.goal = goal;
    }
    /**
     * Map a function for all Goal 
     * @param func
     * @return
     */
    public GoalsTree mapForAll(Consumer<Goal> func) {
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
}
