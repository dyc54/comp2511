package dungeonmania;

import dungeonmania.collectableEntities.durabilityEntities.Durability;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.GoalController;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.FileReader;
import dungeonmania.helpers.FileSaver;
import dungeonmania.helpers.Location;
import dungeonmania.inventories.Inventory;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.Spider;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.staticEntities.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.response.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class DungeonManiaController {
    private Config dungeonConfig;
    private DungeonMap dungeonMap;
    private GoalController goals;
    private String dungeonId;
    private String dungeonName;
    private List<BattleResponse> battles;
    private List<AnimationQueue> animations;
    private Player player;  
    private int timer;
    private int counter;
    private FileSaver fileSaver;

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
        dungeonMap = new DungeonMap();
        battles = new ArrayList<>();
        this.dungeonName = dungeonName;
        timer = 0;
        counter = 0;
        try {
            dungeonConfig = new Config(configName);
            battles = new ArrayList<>();
            fileSaver = new FileSaver(dungeonName, configName, dungeonId);
            dungeonMap.loads(dungeonName, dungeonConfig);
            fileSaver.saveMap(dungeonMap);
            dungeonMap.interactAll().battleAll(battles);
            goals = new GoalController(dungeonName, dungeonConfig);
            player = dungeonMap.getPlayer();
            return getDungeonResponse();
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "'configName' or 'dungeonName' is not a configuration/dungeon that exists");
        }
    }

    private void timerAdd(){
        this.timer++;
    }

    private void checkTimer(int t) {
        if (t == dungeonConfig.spider_spawn_rate) {
            createSpider();
            timer = 0;
        }
    }

    private Location randomLocation() {
        Random random = new Random(timer);
        int x = random.nextInt(dungeonMap.getPlayer().getLocation().getX() + 30);
        int y = random.nextInt(dungeonMap.getPlayer().getLocation().getY() + 30);
        return Location.AsLocation(x, y);
    }

    public void createSpider() {
        // dungeonMap.addEntity(EntityFactory.)
        Spider spider = new Spider("spider", randomLocation(), dungeonConfig.spider_attack, dungeonConfig.spider_health);
        spider.setEntityId(String.format("%s_%s_%d_generated", "spider", spider.getLocation().toString(), counter));
        dungeonMap.addEntity(spider);
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
        player.updatePotionDuration();
        timerAdd();
        checkTimer(timer);
        dungeonMap.UpdateAllEntities();
        dungeonMap.moveAllEntities();
        dungeonMap.battleAll(battles);
        dungeonMap.toString();
        fileSaver.saveAction("useItem", itemUsedId);
        return getDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        System.out.println("************************ Tick movementDirection ********************");
        player.movement(movementDirection.getOffset());
        player.updatePotionDuration();
        timerAdd();
        checkTimer(timer);
        dungeonMap.UpdateAllEntities();
        dungeonMap.moveAllEntities();
        dungeonMap.battleAll(battles);
        dungeonMap.toString();
        fileSaver.saveAction("playerMove", movementDirection.name());
        return getDungeonResponse();

    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        System.out.println(" ------------------- BUILD ------------------- ");
        System.out.println("Current Inventory: ");
        player.getInventory().print();
        player.build(buildable, dungeonConfig);
        fileSaver.saveAction("build", buildable);

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
        fileSaver.saveAction("interact", entityId);
        return getDungeonResponse();
    }
    public DungeonResponse saveGame(String gameName) {
        fileSaver.save(gameName);
        return getDungeonResponse();
    }
    public DungeonResponse loadGame(String gameName) throws IllegalArgumentException{
        System.out.println("************************** LOAD GAME ******************");
        try {
            FileReader.LoadGame(this, gameName, 0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new IllegalArgumentException("gameName is not a valid game name");
        }
        return getDungeonResponse();
    }
    public List<String> allGames() {
        return FileReader.listAllGamesArchives();
    }
    public DungeonResponse rewind(int ticks) {
        return getDungeonResponse();
    }

    /* *********************************************** */
    private DungeonResponse getDungeonResponse() {
        setBattlesResponse();
        goals.hasAchieved(dungeonMap, dungeonMap.getPlayer());
        return new DungeonResponse(dungeonId, dungeonName, getEntitiesResponse(), getItemResponse(), battles,
                    getBuildables(player.getInventory()), goals.toString());
    }


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

    public void setDungeonId(String id) {
        this.dungeonId = id;
        fileSaver.setDungeonId(id);
    }
    public void setDungeonName(String name) {
        this.dungeonName = name;
        fileSaver.setDungeonName(name);
    }
}
