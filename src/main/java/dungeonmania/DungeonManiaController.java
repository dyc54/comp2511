package dungeonmania;

import dungeonmania.Battle.Battle;
import dungeonmania.Battle.Enemy;
import dungeonmania.Goals.GoalController;
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
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
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
    DungeonMap dungeonMap = new DungeonMap();
    GoalController goals;

    // 用于返回DungeonResponse
    private String dungeonId;
    private String dungeonName;
    private List<EntityResponse> entities;
    // private List<ItemResponse> inventory;
    private List<BattleResponse> battles;
    private List<String> buildables;
    private String goalsString;
    private List<AnimationQueue> animations;
    // private Collection<Entity> entitiesList;
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
            battles = new ArrayList<>();
            //dungeonMap = new DungeonMap();
            dungeonMap.loads(dungeonName, dungeonConfig).interactAll().battleAll(battles);
            goals = new GoalController(dungeonName, dungeonConfig);
            // entitiesList = dungeonMap.getAllEntities();
            player = dungeonMap.getPlayer();
            // setPlayer();
            goals.hasAchieved(dungeonMap, player);
            // setGoalsString(dungeonName);
            // System.out.println(getDungeonResponse().getEntities().get(0).getType());
            return getDungeonResponse();
        } catch (IOException e) {
            throw new IllegalArgumentException("'configName' or 'dungeonName' is not a configuration/dungeon that exists");
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
        System.out.println("************************ Tick itemUsedId********************");
        player.useItem(itemUsedId);
        return getDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        System.out.println("************************ Tick movementDirection ********************");

        player.movement(movementDirection.getOffset());
        System.out.println("player:"+player.getLocation());
        dungeonMap.UpdateAllEntities();
        for (Entity entity : dungeonMap.getAllEntities()) {
        // entitiesList = dungeonMap.getAllEntities();
        // for (Entity entity : entitiesList) {
            if (entity.getType().equals("spider")) {
                Spider spider = (Spider) entity;
                spider.movement(dungeonMap);
            } 
            if (entity.getType().equals("zombie_toast")) {
                ZombieToast zombie = (ZombieToast) entity;
                zombie.movement(dungeonMap);
                System.out.println(String.format("zombie_toast moved to %s", zombie.getLocation().toString()));
            } 
            if (entity.getType().equals("zombie_toast_spawner")) {
                ZombieToastSpawner zts = (ZombieToastSpawner) entity;
                zts.ZombieToastSpwanCheck();
                System.out.println("number"+dungeonMap.getEntities("zombie_toast").size());
            }
            if (entity.getType().equals("mercenary")) {
                Mercenary mercenary = (Mercenary) entity;
                mercenary.movement(dungeonMap);
            } 
            if (entity.getType().equals("ally")) {
                MercenaryAlly mercenaryAlly = (MercenaryAlly) entity;
                mercenaryAlly.movement(dungeonMap);
            } 
        }
        // Battle
        dungeonMap.battleAll(battles);
        goals.hasAchieved(dungeonMap, player);
        dungeonMap.toString();
        return getDungeonResponse();
        // cleardisusableItem();
        // String effect = "";
        // if (player.hasEffect()) {
        //     effect = player.getCurrentEffect().applyEffect();
        // }
        // if (dungeonMap.getEntities(player.getLocation()).size() > 0 && !effect.equals("Invisibility")) {
        //     System.out.println("GetEntities");
        //     dungeonMap.getEntities(player.getLocation()).stream().forEach(entity -> System.out.println(entity.toString()));
        //     List<String> removed = new ArrayList<>();
        //     System.out.println(player.getLocation().toString());
        //     for (Entity entity: dungeonMap.getEntities(player.getLocation())) {
        //         System.out.println(entity.toString());
        //         if (entity instanceof Enemy) {
        //             Battle battle = new Battle();
        //             List<String> losers = battle.setBattle(player, (Enemy) entity).startBattle();
        //             losers.stream().forEach(loser -> removed.add(loser));
        //             if (player.getCurrentEffect().applyEffect().equals("Invincibility")) {
        //                 ((EnemyMovement) entity).movement(dungeonMap);
        //             }
        //             battles.add(battle.toResponse());
        //         }
        //     }
        //     removed.stream().forEach(id -> dungeonMap.removeEntity(id));
            // dungeonMap.getEntities(player.getLocation())
            // .stream()
            // .forEach(entity -> {
            //     System.out.println(entity.toString());
            //     if (entity instanceof Enemy) {
            //         Battle battle = new Battle();
            //         String loser = battle.setBattle(player, (Enemy) entity).startBattle();
            //         if (loser.equals("Both")) {
            //             removed.add(player.getEntityId());
            //             removed.add(entity.getEntityId());
            //         } else {
            //             removed.add(loser);
            //         }
            //         battles.add(battle.toResponse());
            //     }
            // });
             

            // System.out.println(dungeonMap.toString());
            // // dungeonMap
            // goals.hasAchieved(dungeonMap, player);
        // }
        // System.out.println("************************ Tick E********************");
        // removed.stream().forEach(action);
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        player.build(buildable, dungeonConfig);
        return getDungeonResponse();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        Entity entity = dungeonMap.getEntity(entityId);
        if (entity == null) {
            throw new IllegalArgumentException("IllegalArgument");
        }
        if (entity.getType().equals("zombie_toast_spawner")) {
            ZombieToastSpawner zombieToastSpawner = (ZombieToastSpawner) entity;
            System.out.println("+++++++++");
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
        setEntitiesResponse();
        setBattlesResponse();
        setBuildables();
        // setItemResponse();
        return new DungeonResponse(dungeonId, dungeonName, entities, getItemResponse(), battles, buildables, goals.toString());
        // return new DungeonResponse(dungeonId, dungeonName, entities, inventory, battles, buildables, "goal");
    }

    /**
     * Create a Response from a list of Battles
     */
    private void setPlayer() {
        this.player = dungeonMap.getPlayer();
    }

    // /**
    //  * achieve goals
    //  */
    // private void setGoalsString() {
    //     this.goalsString = "";
    // }

    // /**
    //  * initialization goals
    //  * 
    //  * @throws IOException
    //  */
    // private void setGoalsString(String dungeonName) throws IOException {
    //     String content = FileReader.LoadFile(dungeonName);
    //     JSONObject json = new JSONObject(content);
    //     JSONObject goals = json.getJSONObject("goal-condition");
    //     this.goalsString = goals.toString();
    // }

    /**
     * Create EntitiesResponses from a list of entities
     */
    private void setEntitiesResponse() {
        List<EntityResponse> entities = new ArrayList<>();
        for (Entity entitie : dungeonMap.getAllEntities()) {
            entities.add(entitie.getEntityResponse());
        }
        this.entities = entities;
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
    private void setBuildables() {

    }

    /**
     * set Animations
     */
    private void setAnimations() {

    }
}
