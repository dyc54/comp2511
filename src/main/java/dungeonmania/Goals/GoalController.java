package dungeonmania.Goals;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.Player;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.FileReader;

public class GoalController {
    GoalsTree root;
    Config config;
    /**
     * 
     * @param json
     * @return
     */
    private GoalsTree loadNode(JSONObject json) {
        String goal = json.getString("goal");
        GoalsTree node = new GoalsTree(goal, GoalController.newGoal(goal));
        if (json.has("subgoals")) {
            JSONArray subgoals = json.getJSONArray("subgoals");
            node.attachGoalA(loadNode(subgoals.getJSONObject(0)));
            node.attachGoalB(loadNode(subgoals.getJSONObject(1)));
        }
        return node;
    }
    /**
     * Create a GoalController object
     * @param path
     * @param config
     * @throws IOException
     */
    public GoalController(String path, Config config) throws IOException {
        this.config = config;
        String content = FileReader.LoadFile(path);
        JSONObject json =  new JSONObject(content);
        root = loadNode(json.getJSONObject("goal-condition")).mapForAll(node -> node.getConfig(config));
    }
    /**
     * Return whether goal has achieved
     * @param map   Dungeon Map
     * @param player waiting 
     * @return
     */
    public boolean hasAchieved(DungeonMap map, Player player) {
        root.mapForAll(node -> node.getMapData(map).getMapData(player));
        return root.hasAchieved();
    }
    /**
     * New a Goal by given type
     * @param str type
     * @return
     */
    private static GoalComponent newGoal(String str) {
        switch (str) {
            case "enemies":
                return new Enemies();
            case "boulders":
                return new Boulders();
            case "treasure":
                return new Treasures();
            case "exit":
                return new GettingExit();
            default:
                return null;
        }
    }
    @Override
    public String toString() {
        String goal = root.toStringAtRoot();
        System.out.println(goal);
        return goal;
    }
  
}
