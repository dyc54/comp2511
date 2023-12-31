package dungeonmania.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.util.Direction;
/**
 * Read file at path ./src/test
 */
public class FileReader {
    
    public static String LoadFile(String FileName) throws IOException {
        try {
            File test = searchFile(new File("./src/test"), FileName+".json");
            File frontEnd = searchFile(new File("./src/main/resources"), FileName+".json");
            Path absPath = (test == null ? frontEnd: test).toPath();
            return new String(Files.readAllBytes(absPath));
        } catch (Exception nulException) {
            throw new IOException("No such file");
        }
    }
    private static File searchFile(File file, String search) {
        if (file.isDirectory()) {
            File[] arr = file.listFiles();
            for (File f : arr) {
                File found = searchFile(f, search);
                if (found != null)
                    return found;
                
            }
        } else {
            if (file.getName().equals(search)) {
                return file;
            }
        }
        return null;
    }
    public static List<String> listAllGamesArchives() {
        HashSet<String> list = new HashSet<>();
        File file = new File(String.format("%s/%d", FileSaver.SAVED_PATH, 0));
        File[] arr = file.listFiles();
        for (File f : arr) {
            String fileName = f.getName().replaceAll("\\[.*?\\].json", "");
            if (fileName.indexOf(".txt") == -1) {
                list.add(fileName);
            }
        }
        return new ArrayList<>(list);
    }
    public static void LoadGame(DungeonManiaController controller, String fileName, int branch) throws IOException {
        FileReader.LoadGame(controller, fileName, branch, 2);
    }
    /**
     * Load Game into controller, 
     * @param deltaTick the number of tick loaded 
     */
    public static void LoadGame(DungeonManiaController controller, String fileName, int branch, int deltaTick) throws IOException {
        String content = FileReader.LoadFile(String.format("%s[%d]", fileName, branch));
        JSONObject json = new JSONObject(content);
        String configName = json.getString("configName");
        controller.newGame(String.format("%s[%s]", fileName, branch), configName);
        controller.setDungeonId(json.getString("dungeonId"));
        controller.setDungeonName(json.getString("dungeonName"));
        JSONArray actions = json.getJSONArray("actions");
        int tickCounter = json.getInt("tickCounter");
        int currTick = 0;
        for (int i = 0; currTick < tickCounter + deltaTick && i < actions.length(); i++) {
            JSONObject action = actions.getJSONObject(i);
            currTick += doAction(controller, action, false, true) ? 1: 0;
        }
    }
    public static void LoadGameTick(DungeonManiaController controller, String fileName, int branch, int tick) throws IOException {
        String content = FileReader.LoadFile(String.format("%s[%d]", fileName, branch));
        JSONObject json = new JSONObject(content);
        JSONArray actions = json.getJSONArray("actions");
        int tickCounter = json.getInt("tickCounter");
        int currTick = 0;
        for (int i = 0; i < actions.length(); i++) {
            JSONObject action = actions.getJSONObject(i);
            if (isTick(action)) {
                currTick++;
            }
            if (currTick == tick) {
                doAction(controller, action, true, true);
            }
        }
    }
    private static boolean isTick(JSONObject action) {
        String func = action.getString("action");
        return func.equals("useItem") || func.equals("playerMove");
    }
    private static boolean doAction(DungeonManiaController controller, JSONObject action, boolean hasTimeTraved, boolean readComments) {
        String func = action.getString("action");
        JSONArray argv = action.getJSONArray("argv");
        boolean isTick = false;
        switch (func) {
            case "useItem":
                try {
                    controller.dotick(argv.getString(0), hasTimeTraved);
                } catch (IllegalArgumentException | JSONException | InvalidActionException e1) {
                    e1.printStackTrace();
                }
                isTick = true;
                break;
            case "playerMove":
                controller.dotick(Direction.valueOf(argv.getString(0)), hasTimeTraved);
                isTick = true;
                break;   
            case "build":
                try {
                    controller.build(argv.getString(0), hasTimeTraved);
                } catch (IllegalArgumentException | JSONException | InvalidActionException e) {
                    e.printStackTrace();
                }
                break;
            case "interact":
                 try {
                    controller.interact(argv.getString(0), hasTimeTraved);
                } catch (IllegalArgumentException | JSONException | InvalidActionException e) {
                    e.printStackTrace();
                }
                break;
            case "rewind":
                controller.rewind(argv.getInt(0));
            default:
                break;
        }
        return isTick;
    }
}
