package dungeonmania;

import dungeonmania.Goals.GoalController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.FileReader;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.response.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DungeonManiaController {
    Config dungeonConfig;
    DungeonMap dungeonMap;
    GoalController goals;

    // 用于返回DungeonResponse
    private String dungeonId;
    private String dungeonName;
    private List<EntityResponse> entities;
    private List<ItemResponse> inventory;
    private List<BattleResponse> battles;
    private List<String> buildables;
    private String goalsString;
    private List<AnimationQueue> animations;
    private Collection<Entity> entitiesList;
    private Player player;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        try {
            dungeonConfig = new Config(configName);
            dungeonMap = new DungeonMap();
            dungeonMap.loads(dungeonName, dungeonConfig);
            /* goals = new GoalController(dungeonName, dungeonConfig); */
            entitiesList = dungeonMap.getAllEntities();
            setGoalsString(dungeonName);
            setPlyer();
            return getDungeonResponse();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return getDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        player.setLocation(movementDirection.getOffset());
        return getDungeonResponse();
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return getDungeonResponse();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return getDungeonResponse();
    }

    /* *********************************************** */
    private DungeonResponse getDungeonResponse() {
        setEntitiesResponse();
        setBattlesResponse();
        setBuildables();
        /* setItemResponse(); */
        return new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables, goalsString);
    }

    /**
     * Create a Response from a list of Battles
     */
    private void setPlyer() {
        for (Entity entitie : entitiesList) {
            if (entitie.getType().equals("player")) {
                this.player = (Player) entitie;
            }
        }
    }

    /**
     * achieve goals
     */
    private void setGoalsString() {
        this.goalsString = "";
    }

    /**
     * initialization goals
     * 
     * @throws IOException
     */
    private void setGoalsString(String dungeonName) throws IOException {
        String content = FileReader.LoadFile(dungeonName);
        JSONObject json = new JSONObject(content);
        JSONObject goals = json.getJSONObject("goal-condition");
        this.goalsString = goals.toString();
    }

    /**
     * Create EntitiesResponses from a list of entities
     */
    private void setEntitiesResponse() {
        List<EntityResponse> entities = new ArrayList<>();
        for (Entity entitie : entitiesList) {
            entities.add(entitie.getEntityResponse());
        }
        this.entities = entities;
    }

    /**
     * Create ItemResponse from of player inventoryList
     */
    private void setItemResponse() {
        this.inventory = player.getItemResponse();
    }

    /**
     * Create a Response from a list of Battles
     */
    private void setBattlesResponse() {

    }

    /**
     * Create a buildables from a list of player inventoryList
     */
    private void setBuildables() {

    }

    /**
     * se tAnimations
     */
    private void setAnimations() {

    }
}
