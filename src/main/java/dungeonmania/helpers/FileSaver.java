package dungeonmania.helpers;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.Player;

public class FileSaver {
    private String dungeonId;
    private String dungeonName;
    private final String configName;
    private final JSONArray initmap;
    private int branch;
    private int tickCounter; 
    private final JSONArray actions;
    private JSONObject goals;
    public final static String SAVED_PATH = "./src/test/resources/Archives";
    public FileSaver(String dungeonName, String configName, String dungeonId) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.configName = configName;
        branch = 0;
        tickCounter = 0;
        actions = new JSONArray();
        initmap = new JSONArray();
        JSONObject temp = new JSONObject();
        String content;
        try {
            content = FileReader.LoadFile(dungeonName);
            JSONObject json = new JSONObject(content);
            temp = json.getJSONObject("goal-condition");
        } catch (IOException e) {
            e.printStackTrace();
        }
        goals = temp;
    }
    public FileSaver(String configName, String dungeonId) {
        this("Builder Generated", configName, dungeonId);
    }
    public static String filePath(String name, int branch) {
        return String.format("%s/%d/%s[%s].json", SAVED_PATH, branch, name, branch);
    }
    public void save(String fileName, int branch) {
        FileWriter file;
        try {
            file = new FileWriter(FileSaver.filePath(fileName, branch),false);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dungeonId", dungeonId);
            jsonObject.put("dungeonName", dungeonName);
            jsonObject.put("configName", configName);
            jsonObject.put("branch", branch);
            jsonObject.put("entities", initmap);
            jsonObject.put("actions", actions);
            jsonObject.put("tickCounter", tickCounter);
            jsonObject.put("goal-condition", goals);
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    public void save(String fileName) {
        save(fileName, 0);
        // FileWriter file;
        // try {
        //     file = new FileWriter(FileSaver.filePath(fileName, branch),false);
        //     // file = new FileWriter(String.format("%s/%s[%s].json", SAVED_PATH, fileName, branch),false);
        //     JSONObject jsonObject = new JSONObject();
        //     jsonObject.put("dungeonId", dungeonId);
        //     jsonObject.put("dungeonName", dungeonName);
        //     jsonObject.put("configName", configName);
        //     jsonObject.put("branch", branch);
        //     jsonObject.put("entities", initmap);
        //     jsonObject.put("actions", actions);
        //     jsonObject.put("tickCounter", tickCounter);
        //     jsonObject.put("goal-condition", goals);
        //     file.write(jsonObject.toString());
        //     file.close();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
        
        
    }
    // private JSONObject entityJSONObject(Entity entity) {
    //     // JSONObject entityObject = new JSONObject();
    //     // entityObject.put("type", entity.getType());
    //     // entityObject.put("x", entity.getLocation().getX());
    //     // entityObject.put("y", entity.getLocation().getY());
    //     // entityObject.put("id", entity.getEntityId());
    //     return entityObject;
    // }
    public FileSaver saveMap(DungeonMap map) {
        // JSONArray entitiesArray = new JSONArray();
        initmap.put(map.getPlayer().toJSONObject());
        for (Entity entity: map) {
            if (!(entity instanceof Player)) {
                initmap.put(entity.toJSONObject());
            }
        }
        // initmap.put("entities", entitiesArray);
        return this;
    }
    // public FileSaver saveAction(String action, int x, int y) {
    //     JSONObject jsonObject = new JSONObject();
    //     jsonObject.put("action", action);
    //     JSONArray args = new JSONArray();
    //     args.put(x);
    //     args.put(y);
    //     jsonObject.put("argv", args);
    //     actions.put(jsonObject);
    //     return this;
    // }
    public void attachGoal(String goal) {
        JSONObject goals = new JSONObject();
        goals.put("goal", goal);
        this.goals = goals;
    }
    public <T> FileSaver saveAction(String action, Boolean isTick, T... argvs) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", action);
        JSONArray args = new JSONArray();
        for (T argv: argvs) {
            args.put(argv);
        }
        jsonObject.put("argv", args);
        actions.put(jsonObject);
        if (isTick) {
            tickCounter++;
        }
        return this;
    }
    public void setDungeonName(String name) {
        this.dungeonName = name;
    }
    public void setDungeonId(String id) {
        this.dungeonId = id;
    }
}   
