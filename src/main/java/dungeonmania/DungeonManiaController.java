package dungeonmania;

import dungeonmania.bosses.Assassin;
// import dungeonmania.collectableEntities.durabilityEntities.Durability;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.goals.GoalController;
import dungeonmania.goals.GoalsTree;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DijstraAlgorithm;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.FileReader;
import dungeonmania.helpers.FileSaver;
import dungeonmania.helpers.Location;
import dungeonmania.helpers.RandomMapGenerator;
import dungeonmania.helpers.Timer;
import dungeonmania.inventories.Inventory;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.Spider;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.staticEntities.ZombieToastSpawner;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;
import dungeonmania.response.models.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
    // private int timer;
    private int counter;
    // private int tickCounter;
    private FileSaver fileSaver;
    private boolean isTimeTravling;
    private int deltaTickAfterTimeTraveling;
    private int currbranch;
    private Timer Ticktimer;
    // TODO:
    // public boolean TIMETRAVEL_FUNCA
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
        // timer = 0;
        counter = 0;
        currbranch = 0;
        // tickCounter = 0;
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

    // private void timerAdd(){
    //     this.timer++;
    // }

    private void checkTimer(int t) {
        // boolean spawn = (Ticktimer.getTime() % dungeonConfig.spider_spawn_rate) == 0;
        if (dungeonConfig.spider_spawn_rate != 0 
            && (Ticktimer.getTime() % dungeonConfig.spider_spawn_rate) == 0) {
            createSpider();
        }
    }

    private Location randomLocation() {
        Random random = new Random(Ticktimer.getTime());
        int x = random.nextInt(Math.abs(dungeonMap.getPlayer().getLocation().getX() + 30));
        int y = random.nextInt(Math.abs(dungeonMap.getPlayer().getLocation().getY() + 30));
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
        dotick(itemUsedId, false);
        // updateTimeTravelStatus();
        // runTick(tickCounter);
        // updateTimeTravelStatus();
        // deltaTickAfterTimeTraveling--;
        return getDungeonResponse();
        // return 
    }

    /**
     * Tick Operation for older player if `timeTraveledPlayer` is True
     * otherwise, it will operate current player
    */
    public void dotick(String itemUsedId, boolean timeTraveledPlayer) throws IllegalArgumentException, InvalidActionException {
        System.out.println("************************ Tick itemUsedId********************");
        Player player = this.player;
        if (timeTraveledPlayer) {
            player = dungeonMap.getPlayer();
        } else {
            
            // tickCounter++;
            Ticktimer.addTime();
            fileSaver.saveAction("useItem", true, itemUsedId);
        }
        // timerAdd();
        // checkTimer(timer);
        player.updateSceptreRound();
        player.useItem(itemUsedId);
        player.updatePotionDuration();
        
        dungeonMap.UpdateAllEntities();
        dungeonMap.moveAllEntities();
        dungeonMap.battleAll(battles, player);
        dungeonMap.toString();
        if (!timeTraveledPlayer) {
            updateTimeTravelStatus();
            runTick(Ticktimer.getTime());
            updateTimeTravelStatus();
            deltaTickAfterTimeTraveling--;
        }
        // tickCounter++;
        // return getDungeonResponse();

    }
    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        dotick(movementDirection, false);
        // updateTimeTravelStatus();
        // runTick(tickCounter);
        // // tickCounter++;
        // updateTimeTravelStatus();
        // deltaTickAfterTimeTraveling--;
        
        return getDungeonResponse();
    }

    /**
     * Tick Operation for older player if `timeTraveledPlayer` is True
     * otherwise, it will operate current player
     */
    public void dotick(Direction movementDirection, boolean timeTraveledPlayer) {
        System.out.println("************************ Tick movementDirection ********************");
        Player player = this.player;
        if (timeTraveledPlayer) {
            player = dungeonMap.getPlayer();
        } else {
            // tickCounter++;
            Ticktimer.addTime();
            checkTimer(Ticktimer.getTime());
            fileSaver.saveAction("playerMove", true, movementDirection.name());
        }
        player.movement(movementDirection.getOffset());
        player.updatePotionDuration();
        player.updateSceptreRound();
        dungeonMap.UpdateAllEntities();
        dungeonMap.moveAllEntities();
        dungeonMap.battleAll(battles, player);
        dungeonMap.toString();
        if (!timeTraveledPlayer && dungeonMap.isTimeTravelPortal(player.getLocation())) {
            // fileSaver.saveAction("mark", false, "c");
            doRewind(30, 2);
            // player.setLocation(player.getPreviousLocation());
            fileSaver.saveAction("playerMove", false, Location.inverseDirection(movementDirection), "MOVE ELDER_SELF ONLY");
        } else if (!timeTraveledPlayer) {
            // timerAdd();
            // checkTimer(timer);
            updateTimeTravelStatus();
            runTick(Ticktimer.getTime());
            // tickCounter++;
            updateTimeTravelStatus();
            deltaTickAfterTimeTraveling--;
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
        System.out.println(" ------------------- BUILD ------------------- ");
        Player player = this.player;
        if (timeTraveledPlayer) {
            player = dungeonMap.getPlayer();
        } else {
            fileSaver.saveAction("build", false, buildable);
        }
        System.out.println("Current Inventory: ");
        player.getInventory().print();

        player.build(buildable, dungeonConfig);
        fileSaver.saveAction("build", false, buildable);
        // goals = new GoalsTree("exit");
        
        return getDungeonResponse();

    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        System.out.println("INTERACT-----------------------");
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
        // TODO: ADD MORE BUILDABLES
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
        System.out.println("************************** LOAD GAME ******************");
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
        System.out.println(String.format("--------- TIME TRAVEL %d --------------\n Starting running", ticks));
        Player backupPlayer = player;
        FileSaver backupFileSaver = fileSaver;
        fileSaver.save(dungeonName, branch);
        try {
            FileReader.LoadGame(this, dungeonName, branch, -1 * ticks);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("************************** end running *******************");
        dungeonMap.getPlayer().setType("older_player");
        dungeonMap.print();
        this.player = backupPlayer;
        deltaTickAfterTimeTraveling = ticks;
        // tickCounter = backuptickCounter - ticks;
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
        System.out.println("Update updateTimeTravelStatus");
        if (isTimeTravling) {
            if (deltaTickAfterTimeTraveling <= 0) {
                isTimeTravling = false;
                dungeonMap.setPlayer(this.player);
            } 
        }
        
    }

    /* public DijstraAlgorithm testDijstraAlgorithm(){
        Entity enemy = null;
        for(Entity e : dungeonMap.getAllEntities() ){
            if(e instanceof Mercenary){
                enemy = e;
            }
        }
        DijstraAlgorithm da = new DijstraAlgorithm(player, dungeonMap, enemy);
        return  da;
    } */
    

}
