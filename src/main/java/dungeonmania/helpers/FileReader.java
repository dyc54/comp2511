package dungeonmania.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
    public static String LoadFile(String FileName, int branch) throws IOException {
        return FileReader.LoadFile(String.format("%s[%s]", FileName, branch));
    }
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
                System.out.println("FILE FOUND\n->"+file.getAbsolutePath());
                return file;
            }
        }
        return null;
    }
    public static List<String> listAllGamesArchives() {
        ArrayList<String> list = new ArrayList<>();
        File file = new File(FileSaver.SAVED_PATH);
        File[] arr = file.listFiles();
        for (File f : arr) {
            String fileName = f.getName().replaceAll("\\[.*?\\].json", "");
            list.add(fileName);
        }
        return list;
    }
    public static void LoadGame(DungeonManiaController controller, String fileName, int branch) throws IOException {
        String content = FileReader.LoadFile(fileName, branch);
        JSONObject json = new JSONObject(content);
        String configName = json.getString("configName");
        controller.newGame(String.format("%s[%s]", fileName, branch), configName);
        controller.setDungeonId(json.getString("dungeonId"));
        controller.setDungeonName(json.getString("dungeonName"));
        JSONArray actions = json.getJSONArray("actions");
        for (int i = 0; i < actions.length(); i++) {
            JSONObject action = actions.getJSONObject(i);
            doAction(controller, action);
        }
    }
    private static void doAction(DungeonManiaController controller, JSONObject action) {
        String func = action.getString("action");
        JSONArray argv = action.getJSONArray("argv");
        switch (func) {
            case "useItem":
                try {
                    controller.tick(argv.getString(0));
                } catch (IllegalArgumentException | JSONException | InvalidActionException e1) {
                    e1.printStackTrace();
                }
                break;
            case "playerMove":
                controller.tick(Direction.valueOf(argv.getString(0)));
                break;   
            case "build":
                try {
                    controller.build(argv.getString(0));
                } catch (IllegalArgumentException | JSONException | InvalidActionException e) {
                    e.printStackTrace();
                }
                break;
            case "interact":
                 try {
                    controller.interact(argv.getString(0));
                } catch (IllegalArgumentException | JSONException | InvalidActionException e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}
