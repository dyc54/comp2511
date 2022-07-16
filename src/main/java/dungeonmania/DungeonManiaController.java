package dungeonmania;

import dungeonmania.Battle.Battle;
import dungeonmania.Battle.Enemy;
import dungeonmania.Goals.GoalController;
import dungeonmania.Inventories.Inventory;
import dungeonmania.MovingEntities.Mercenary;
import dungeonmania.MovingEntities.MercenaryAlly;
import dungeonmania.MovingEntities.Spider;
import dungeonmania.MovingEntities.ZombieToast;
import dungeonmania.StaticEntities.ZombieToastSpawner;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.FileReader;
import dungeonmania.helpers.Location;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.response.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DungeonManiaController {
    Config dungeonConfig;
    DungeonMap dungeonMap = new DungeonMap();
    GoalController goals;

    // 用于返回DungeonResponse
    private String dungeonId;
    private String dungeonName;
    // private List<EntityResponse> entities;
    // private List<ItemResponse> inventory;
    private List<BattleResponse> battles;
    private List<String> buildables = new ArrayList<>();
    private String goalsString;
    private List<AnimationQueue> animations;
    // private Collection<Entity> entitiesList;
    private Player player;  
    int timer = 0;
    int spider_spawn_rate;
    int spider_attack;
    int spider_health;

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
        this.dungeonId = UUID.randomUUID().toString();
        battles = new ArrayList<>();
        this.dungeonName = dungeonName;
        try {
            dungeonConfig = new Config(configName);
            battles = new ArrayList<>();
            dungeonMap.loads(dungeonName, dungeonConfig).interactAll().battleAll(battles);
            goals = new GoalController(dungeonName, dungeonConfig);
            player = dungeonMap.getPlayer();
            spider_spawn_rate = dungeonConfig.spider_spawn_rate;
            spider_attack = dungeonConfig.spider_attack;
            spider_health = dungeonConfig.spider_health;
            // goals.hasAchieved(dungeonMap, player);
            // setGoalsString(dungeonName);
            // System.out.println(getDungeonResponse().getEntities().get(0).getType());
            return getDungeonResponse();
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "'configName' or 'dungeonName' is not a configuration/dungeon that exists");
        }
        /* player = dungeonMap.getPlayer();
        goals.hasAchieved(dungeonMap, player);
        return getDungeonResponse(); */

    }

    public void timerAdd(){
        this.timer++;
    }

    public void checkTimer(int t) {
        if (t == spider_spawn_rate) {
            createSpider();
            timer = 0;
        }
    }

    public Location randomLocation() {
        Random random = new Random();
        int x = random.nextInt(dungeonMap.getPlayer().getLocation().getX() + 30);
        int y = random.nextInt(dungeonMap.getPlayer().getLocation().getY() + 30);
        return Location.AsLocation(x, y);
    }

    public void createSpider() {
        dungeonMap.addEntity(new Spider("spider", randomLocation(), spider_attack, spider_health));
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
        System.out.println("************************ Tick itemUsedId********************");
        timerAdd();
        checkTimer(timer);
        player.useItem(itemUsedId);
        dungeonMap.battleAll(battles);
        // goals.hasAchieved(dungeonMap, player);
        return getDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        System.out.println("************************ Tick movementDirection ********************");
        player.movement(movementDirection.getOffset());
        timerAdd();
        checkTimer(timer);
        dungeonMap.UpdateAllEntities();
        dungeonMap.moveAllEntities();
        // for (Entity entity : dungeonMap.getAllEntities()) {
        //     if (entity.getType().equals("spider")) {
        //         Spider spider = (Spider) entity;
        //         spider.movement(dungeonMap);
        //     }
        //     if (entity.getType().equals("zombie_toast")) {
        //         ZombieToast zombie = (ZombieToast) entity;
        //         zombie.movement(dungeonMap);
        //         System.out.println(String.format("zombie_toast moved to %s", zombie.getLocation().toString()));
        //     }
        //     if (entity.getType().equals("zombie_toast_spawner")) {
        //         ZombieToastSpawner zts = (ZombieToastSpawner) entity;
        //         zts.ZombieToastSpwanCheck();
        //         System.out.println("number" + dungeonMap.getEntities("zombie_toast").size());
        //     }
        //     if (entity.getType().equals("mercenary")) {
        //         Mercenary mercenary = (Mercenary) entity;
        //         mercenary.movement(dungeonMap);
        //     } 
        // }
        // Battle
        dungeonMap.battleAll(battles);
        // goals.hasAchieved(dungeonMap, player);
        dungeonMap.toString();
        return getDungeonResponse();

    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        System.out.println(" ------------------- BUILD ------------------- ");
        System.out.println("Current Inventory: ");
        player.getInventory().print();
        player.build(buildable, dungeonConfig, 0);
        // buildables.add(buildable);
        return getDungeonResponse();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity entity = dungeonMap.getEntity(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("entityId is not a valid entity ID");
        }
        if (entity.getType().equals("zombie_toast_spawner")) {
            ZombieToastSpawner zombieToastSpawner = (ZombieToastSpawner) entity;
            if (!zombieToastSpawner.interact(player, dungeonMap)) {
                throw new InvalidActionException("Invaild action");
            }
        }
        if (entity.getType().equals("mercenary")) {
            Mercenary mercenary = (Mercenary) entity;
            if (!mercenary.interact(player, dungeonMap)) {
                throw new InvalidActionException("Invaild action");
            }
        }
        return getDungeonResponse();
    }

    /* *********************************************** */
    private DungeonResponse getDungeonResponse() {
        // setEntitiesResponse();
        setBattlesResponse();
        // setItemResponse();
        goals.hasAchieved(dungeonMap, dungeonMap.getPlayer());
        return new DungeonResponse(dungeonId, dungeonName, getEntitiesResponse(), getItemResponse(), battles,
                    getBuildables(player.getInventory()), goals.toString());
    }


    // /**
    // * achieve goals
    // */
    // private void setGoalsString() {
    // this.goalsString = "";
    // }

    // /**
    // * initialization goals
    // *
    // * @throws IOException
    // */
    // private void setGoalsString(String dungeonName) throws IOException {
    // String content = FileReader.LoadFile(dungeonName);
    // JSONObject json = new JSONObject(content);
    // JSONObject goals = json.getJSONObject("goal-condition");
    // this.goalsString = goals.toString();
    // }

    /**
     * Create EntitiesResponses from a list of entities
     */
    private List<EntityResponse> getEntitiesResponse() {
        List<EntityResponse> entities = new ArrayList<>();
        for (Entity entitie : dungeonMap.getAllEntities()) {
            entities.add(entitie.getEntityResponse());
        }
        return entities;
        // this.entities = entities;
    }

    /**
     * Create ItemResponse from of player inventoryList
     */
    private List<ItemResponse> getItemResponse() {
        return player.getItemResponse();
    }

    /**
     * Create a Response from a list of Battles
     */
    private void setBattlesResponse() {

    }

    /**
     * Create a buildables from a list of player inventoryList
     */
    private List<String> getBuildables(Inventory inventory) {
        // if (buildResult == null) return;

        // buildables.add(buildResult);
        // System.out.println("CHECK BUILDABLES");
        return Arrays.asList(BuildableEntityFactory.newRecipe("bow"),
                    BuildableEntityFactory.newRecipe("shield")).stream()
                    .filter(recipe -> recipe.isSatisfied(inventory))
                    .map(recipe -> recipe.getRecipeName())
                    .collect(Collectors.toList());
    }

    /**
     * set Animations
     */
    private void setAnimations() {

    }
}
