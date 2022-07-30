package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.GoalController;
import dungeonmania.goals.GoalsTree;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.*;
import dungeonmania.inventories.Inventory;
import dungeonmania.movingEntities.Spider;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
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
    private int counter;
    private FileSaver fileSaver;
    private boolean isTimeTravling;
    private int deltaTickAfterTimeTraveling;
    private int currbranch;
    private Timer Ticktimer;

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
    private void initController() {
        this.dungeonId = UUID.randomUUID().toString();
        dungeonMap = new DungeonMap();
        battles = new ArrayList<>();
        counter = 0;
        currbranch = 0;
        deltaTickAfterTimeTraveling = 0;
        isTimeTravling = false;
        Ticktimer = new Timer();
        battles = new ArrayList<>();
    }

    /**
     * /game/new/
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        initController();
        this.dungeonName = dungeonName;
        try {
            dungeonConfig = new Config(configName);
            fileSaver = new FileSaver(dungeonName, configName, dungeonId);
            dungeonMap.setTimer(Ticktimer);
            dungeonMap.loads(dungeonName, dungeonConfig);
            fileSaver.saveMap(dungeonMap);
            player = dungeonMap.getPlayer();
            dungeonMap.interactAll().battleAll(battles, player);
            goals = new GoalController(dungeonName, dungeonConfig);
            dungeonMap.UpdateAllLogicalEntities();
            return getDungeonResponse();
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    "'configName' or 'dungeonName' is not a configuration/dungeon that exists");
        }
    }

    /**
     * /game/new/generate
     */
    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String configName) {
        initController();
        RandomMapGenerator walls = new RandomMapGenerator(xStart, yStart, xEnd, yEnd);
        dungeonMap = new DungeonMap();
        fileSaver = new FileSaver(configName, dungeonId);
        try {
            dungeonConfig = new Config(configName);
            dungeonMap.setTimer(Ticktimer);
            dungeonMap.loads(walls.start(), dungeonConfig);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("configName is not a configuration that exists");
        }
        fileSaver.saveMap(dungeonMap);
        player = dungeonMap.getPlayer();
        dungeonMap.interactAll().battleAll(battles, player);
        dungeonMap.UpdateAllLogicalEntities();
        goals = new GoalController(new GoalsTree("exit", GoalController.newGoal("exit")), dungeonConfig);
        fileSaver.attachGoal("exit");
        return getDungeonResponse();
    }

    private void checkTimer(int t) {
        if (dungeonConfig.spiderSpawnRate != 0 
            && (Ticktimer.getTime() % dungeonConfig.spiderSpawnRate) == 0) {
            createSpider();
        }
    }

    private Location randomLocation() {
        Random random = new Random(Ticktimer.getTime());
        int x = random.nextInt(60) + dungeonMap.getPlayer().getLocation().getX() - 30;
        int y = random.nextInt(60) + dungeonMap.getPlayer().getLocation().getY() - 30;
        return Location.AsLocation(x, y);
    }

    public void createSpider() {
        Spider spider = new Spider("spider", randomLocation(), dungeonConfig.spiderAttack, dungeonConfig.spiderHealth);
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
        dotick(itemUsedId, false);
        return getDungeonResponse();
    }

    /**
     * Tick Operation for older player if `timeTraveledPlayer` is True
     * otherwise, it will operate current player
    */
    public void dotick(String itemUsedId, boolean timeTraveledPlayer) throws IllegalArgumentException, InvalidActionException {
        Player player = this.player;
        if (timeTraveledPlayer) {
            player = dungeonMap.getPlayer();
        } else {
            Ticktimer.addTime();
            fileSaver.saveAction("useItem", true, itemUsedId);
        }
        player.updateSceptreRound();
        player.useItem(itemUsedId);
        dungeonMap.UpdateAllEntities();
        dungeonMap.moveAllEntities();
        dungeonMap.battleAll(battles, player);
        dungeonMap.toString();
        if (!timeTraveledPlayer) {
            updateTimeTravelStatus();
            runTick(Ticktimer.getTime());
            updateTimeTravelStatus();
            deltaTickAfterTimeTraveling--;
            this.player.updatePotionDuration();
        } 
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        dotick(movementDirection, false);
        return getDungeonResponse();
    }

    /**
     * Tick Operation for older player if `timeTraveledPlayer` is True
     * otherwise, it will operate current player
     */
    public void dotick(Direction movementDirection, boolean timeTraveledPlayer) {
        Player player = this.player;
        if (timeTraveledPlayer) {
            player = dungeonMap.getPlayer();
        } else {
            Ticktimer.addTime();
            checkTimer(Ticktimer.getTime());
            fileSaver.saveAction("playerMove", true, movementDirection.name());
        }
        player.movement(movementDirection.getOffset());
        player.updateSceptreRound();
        dungeonMap.UpdateAllEntities();
        dungeonMap.moveAllEntities();
        dungeonMap.battleAll(battles, player);
        dungeonMap.toString();
        if (!timeTraveledPlayer && dungeonMap.isTimeTravelPortal(player.getLocation())) {
            doRewind(30, 2);
            fileSaver.saveAction("playerMove", false, Location.inverseDirection(movementDirection), "MOVE ELDER_SELF ONLY");
        } else if (!timeTraveledPlayer) {
            updateTimeTravelStatus();
            runTick(Ticktimer.getTime());
            updateTimeTravelStatus();
            deltaTickAfterTimeTraveling--;
            this.player.updatePotionDuration();
        } 
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        return build(buildable, false);
    }

    /**
     * Build Operation for older player if `timeTraveledPlayer` is True
     * otherwise, it will operate for current player
    */
    public DungeonResponse build(String buildable, boolean timeTraveledPlayer)  throws IllegalArgumentException, InvalidActionException {
        Player player = this.player;
        if (timeTraveledPlayer) {
            player = dungeonMap.getPlayer();
        } else {
            fileSaver.saveAction("build", false, buildable);
        }
        player.build(buildable, dungeonConfig);
        fileSaver.saveAction("build", false, buildable);
        return getDungeonResponse();

    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return interact(entityId, false);
    }

    /**
     * Interact Operation for older player if `timeTraveledPlayer` is True
     * otherwise, it will operate for current player
    */
    public DungeonResponse interact(String entityId, boolean timeTraveledPlayer) throws IllegalArgumentException, InvalidActionException {
        Player player = this.player;
        if (timeTraveledPlayer) {
            player = dungeonMap.getPlayer();
        } else {
            fileSaver.saveAction("interact", false, entityId);
        }
        Entity entity = dungeonMap.getEntity(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("entityId is not a valid entity ID");
        }
        if (entity instanceof Interact) {
            Interact interactEntity = (Interact) entity;
            if (!interactEntity.interact(player, dungeonMap)) {
                throw new InvalidActionException("Invaid action");
            }
        }
        return getDungeonResponse();
    }
    

    /* *********************************************** */
    private DungeonResponse getDungeonResponse() {
        setBattlesResponse();
        goals.hasAchieved(dungeonMap, player);
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
        if (isTimeTravling) {
            entities.add(player.getEntityResponse());
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
                    BuildableEntityFactory.newRecipe("shield"),
                    BuildableEntityFactory.newRecipe("midnight_armour"),
                    BuildableEntityFactory.newRecipe("sceptre")).stream()
                    .filter(recipe -> recipe.CountItem(inventory.view()).isSatisfied() 
                                    && recipe.getPrerequisite().allMatch(dungeonMap.iterator()).isSatisfied())
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

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        fileSaver.save(name);
        return getDungeonResponse();
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        try {
            FileReader.LoadGame(this, name, 0);
        } catch (IOException e) {
            throw new IllegalArgumentException("gameName is not a valid game name");
        }
        return getDungeonResponse();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return FileReader.listAllGamesArchives();
    }

    public DungeonResponse rewind(int ticks) {
        if (ticks <= 0 || ticks > Ticktimer.getTime()) {
            throw new IllegalArgumentException("ticks must be either 1 or 5");
        }
        doRewind(ticks, 1);
        fileSaver.saveAction("rewind", false, ticks);
        return getDungeonResponse();
    }

    public void doRewind(int ticks, int branch) {
        Player backupPlayer = player;
        FileSaver backupFileSaver = fileSaver;
        fileSaver.save(dungeonName, branch);
        try {
            FileReader.LoadGame(this, dungeonName, branch, -1 * ticks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dungeonMap.getPlayer().setType("older_player");
        this.player = backupPlayer;
        deltaTickAfterTimeTraveling = ticks;
        isTimeTravling = true;
        fileSaver = backupFileSaver;
        currbranch = branch;
    }

    private void runTick(int tick) {
        if (isTimeTravling) {
            try {
                FileReader.LoadGameTick(this, dungeonName, currbranch, tick);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTimeTravelStatus() {
        if (isTimeTravling) {
            if (deltaTickAfterTimeTraveling <= 0) {
                isTimeTravling = false;
                dungeonMap.setPlayer(this.player);
            } 
        }
        
    }

}
