package dungeonmania.helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import dungeonmania.Entity;
import dungeonmania.Player;

public class FileSaver {
    private final String dungeonId;
    private final String dungeonName;
    private final String configName;
    private final JSONObject initmap;
    private int branch;
    private final JSONArray actions;
    public final static String SAVED_PATH = "./src/test/resources/Archives";
    public FileSaver(String dungeonName, String configName, String dungeonId) {
        this.dungeonId = dungeonId;
        this.dungeonName = dungeonName;
        this.configName = configName;
        branch = 0;
        actions = new JSONArray();
        initmap = new JSONObject();
    }
    
    public void save() {
        FileWriter file;
        try {
            file = new FileWriter(String.format("%s/%s[%s].json", SAVED_PATH, dungeonName, branch));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dungeonId", dungeonId);
            jsonObject.put("dungeonName", dungeonName);
            jsonObject.put("configName", configName);
            jsonObject.put("branch", branch);
            jsonObject.put("initMap", initmap);
            jsonObject.put("actions", actions);
            file.write(jsonObject.toString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
    }
    private JSONObject entityJSONObject(Entity entity) {
        JSONObject entityObject = new JSONObject();
        entityObject.put("type", entity.getType());
        entityObject.put("x", entity.getLocation().getX());
        entityObject.put("y", entity.getLocation().getY());
        entityObject.put("id", entity.getEntityId());
        return entityObject;
    }
    public FileSaver saveMap(DungeonMap map) {
        JSONArray entitiesArray = new JSONArray();
        entitiesArray.put(entityJSONObject(map.getPlayer()));
        for (Entity entity: map) {
            if (!(entity instanceof Player)) {
                entitiesArray.put(entityJSONObject(entity));
            }
        }
        initmap.put("initMap", entitiesArray);
        return this;
    }
    public FileSaver saveAction(String action, int x, int y) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", action);
        JSONArray args = new JSONArray();
        args.put(x);
        args.put(y);
        jsonObject.put("argv", args);
        actions.put(jsonObject);
        return this;
    }
    public FileSaver saveAction(String action, String... argvs) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", action);
        JSONArray args = new JSONArray();
        for (String argv: argvs) {
            args.put(argv);
        }
        jsonObject.put("argv", args);
        actions.put(jsonObject);
        return this;
    }
}
