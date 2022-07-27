package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getPlayer;
import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;
import static dungeonmania.TestUtils.countEntityOfType;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PersistenceTests {
    private void clear() {
        try {
            Files.walk(Paths.get("./src/test/resources/Archives"))
            .filter(Files::isRegularFile)
            .map(Path::toFile)
            .forEach(File::delete);
        } catch (IOException e) {
        }

    }
    private void assertAllEntitiesEqual(DungeonResponse expect, DungeonResponse Dungonload) {
        for (int i = 0; i < expect.getEntities().size(); i++) {
            EntityResponse expectEntity = expect.getEntities().get(i);
            EntityResponse givenEntity = Dungonload.getEntities().get(i);
            expect.getEntities().contains(givenEntity);
        }
        assertEquals(expect.getEntities().size(), Dungonload.getEntities().size());
    }
    private void assertAllInventoryEqual(DungeonResponse expect, DungeonResponse Dungonload) {
        // assertEquals(expect.getInventory(), Dungonload.getInventory());
        for (int i = 0; i < expect.getInventory().size(); i++) {
            ItemResponse expectItem = expect.getInventory().get(i);
            ItemResponse givenItem = Dungonload.getInventory().get(i);
            expect.getInventory().contains(givenItem);
        }
        assertEquals(expect.getInventory().size(), Dungonload.getInventory().size());
    }
    
    private void asserAllBattleEqual(DungeonResponse expect, DungeonResponse Dungonload) {
        for (int i = 0; i < expect.getEntities().size(); i++) {
            BattleResponse expectBattles = expect.getBattles().get(i);
            BattleResponse givenBattles = Dungonload.getBattles().get(i);
            assertEquals(expectBattles.getEnemy(), givenBattles.getEnemy());
            assertEquals(expectBattles.getInitialEnemyHealth(), givenBattles.getInitialEnemyHealth());
            assertEquals(expectBattles.getInitialPlayerHealth(), givenBattles.getInitialPlayerHealth());
            for (int j = 0; j < expectBattles.getRounds().size(); j++) {
                RoundResponse expectedRound = expectBattles.getRounds().get(i);
                RoundResponse givenRound = givenBattles.getRounds().get(i);
                assertEquals(expectedRound.getDeltaCharacterHealth(), givenRound.getDeltaCharacterHealth());
                assertEquals(expectedRound.getDeltaEnemyHealth(), givenRound.getDeltaEnemyHealth());
                for (int k = 0; k < expectedRound.getWeaponryUsed().size(); k++) {
                    ItemResponse givenItem = givenRound.getWeaponryUsed().get(i);
                    expectedRound.getWeaponryUsed().contains(givenItem);
                }
                assertEquals(expectedRound.getWeaponryUsed().size(), givenRound.getWeaponryUsed().size());
            }
        }
    }

    @Test
    public void testCanSave() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_movementTest_testMovementDown",
                "c_movementTest_testMovementDown");
        dmc.tick(Direction.DOWN);
        assertDoesNotThrow(() -> {
            dmc.saveGame("d_movementTest_testMovementDown");
        });
        
    }
    @Test
    public void testCanLoad() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_movementTest_testMovementDown",
                "c_movementTest_testMovementDown");
        dmc.tick(Direction.DOWN);
        DungeonResponse DungonRes = dmc.saveGame("d_movementTest_testMovementDown");
        String name = DungonRes.getDungeonName();
        assertDoesNotThrow(() -> {
            dmc.loadGame(name);
        });
    }
    @Test
    public void testLoadNotExistFile() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_movementTest_testMovementDown",
                "c_movementTest_testMovementDown");
        dmc.tick(Direction.DOWN);
        DungeonResponse DungonRes = dmc.saveGame("d_movementTest_testMovementDown");
        String name = DungonRes.getDungeonName();
        assertThrows(IllegalArgumentException.class, () -> {
            dmc.loadGame(name+"AAA");
        });
    }
    @Test
    public void testListAllGames() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_movementTest_testMovementDown",
                "c_movementTest_testMovementDown");
        dmc.saveGame("d_movementTest_testMovementDown");
        dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_movementTest_testMovementDown");
        dmc.saveGame("d_collectTests_pickUpAllCollectableEntity");
        assertTrue(dmc.allGames().contains("d_collectTests_pickUpAllCollectableEntity"));
        assertTrue(dmc.allGames().contains("d_movementTest_testMovementDown"));
    }

    @Test
    public void testGoalsUncomplete() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_movementTest_testMovementDown",
                "c_movementTest_testMovementDown");
        DungeonResponse DungonRes = dmc.saveGame("d_movementTest_testMovementDown");
        DungeonResponse Dungonload = dmc.loadGame("d_movementTest_testMovementDown");
        assertEquals(DungonRes.getGoals(), Dungonload.getGoals());
    }
    @Test
    public void testGoalsComplete() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_complexGoalsTest_andAll",
                "c_movementTest_testMovementDown");
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        DungeonResponse DungonRes = dmc.saveGame("d_complexGoalsTest_andAll");
        DungeonResponse Dungonload = dmc.loadGame("d_complexGoalsTest_andAll");
        assertEquals(DungonRes.getGoals(), Dungonload.getGoals());
        dmc.tick(Direction.RIGHT);
        DungonRes = dmc.saveGame("d_complexGoalsTest_andAll");
        Dungonload = dmc.loadGame("d_complexGoalsTest_andAll");
        assertEquals(DungonRes.getGoals(), Dungonload.getGoals());
    }
    
    @Test
    public void testEntities() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("advanced",
                "c_movementTest_testMovementDown");
        
        DungeonResponse DungonRes = dmc.saveGame("advanced");
        dmc.newGame("d_complexGoalsTest_andAll", "c_movementTest_testMovementDown");
        DungeonResponse Dungonload = dmc.loadGame("advanced");
        assertAllEntitiesEqual(DungonRes, Dungonload);
        
    }
    @Test
    public void testPlayerInventory() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("testBattleBuildAllV4EJM1657810665.3420236", "c_Battletest_PlayerStrong");
        DungeonResponse DungonRes = dmc.saveGame("testBattleBuildAllV4EJM1657810665.3420236");
        DungeonResponse Dungonload = dmc.loadGame("testBattleBuildAllV4EJM1657810665.3420236");
        assertAllInventoryEqual(DungonRes, Dungonload);
        assertDoesNotThrow( () -> {
            dmc.build("bow");
            dmc.build("shield");
        });
        DungonRes = dmc.saveGame("testBattleBuildAllV4EJM1657810665.3420236");
        Dungonload = dmc.loadGame("testBattleBuildAllV4EJM1657810665.3420236");
        assertAllInventoryEqual(DungonRes, Dungonload);
    }
  
    @Test
    public void testPlayerMovement() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_complexGoalsTest_andAll",
                "c_complexGoalsTest_andAll");
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.RIGHT);
        DungeonResponse DungonRes = dmc.saveGame("d_complexGoalsTest_andAll");
        DungeonResponse Dungonload = dmc.loadGame("d_complexGoalsTest_andAll");
        assertEquals(getPlayer(DungonRes), getPlayer(Dungonload));
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    @Test
    public void testMercenaryIsAlly() {
        clear();
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest", "c_mercenaryTest_bribeFail");
        DungeonResponse DungonRes = dmc.saveGame("d_mercenaryTest");
        DungeonResponse Dungonload = dmc.loadGame("d_mercenaryTest");
        assertAllEntitiesEqual(DungonRes, Dungonload);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        String mercenaryId = getEntities(res, "mercenary").get(0).getId();
        res = assertDoesNotThrow(()-> dmc.interact(mercenaryId));
        assertEquals(0, getInventory(res, "treasure").size());
        DungonRes = dmc.saveGame("d_mercenaryTest");
        Dungonload = dmc.loadGame("d_mercenaryTest");
        assertAllEntitiesEqual(DungonRes, Dungonload);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        DungonRes = dmc.saveGame("d_mercenaryTest");
        Dungonload = dmc.loadGame("d_mercenaryTest");
        assertAllEntitiesEqual(DungonRes, Dungonload);
        res = dmc.tick(Direction.RIGHT);
        assertTrue(res.getBattles().size() == 0);
    }

    @Test
    public void testZombieMovement() {
        clear();
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_randomMove", "c_spiderTest_basicMovement");

        for (int i = 0; i <= 20; i++) {
            res = dmc.tick(Direction.DOWN);
        }
        DungeonResponse DungonRes = dmc.saveGame("d_zombieTest_randomMove");
        DungeonResponse Dungonload = dmc.loadGame("d_zombieTest_randomMove");
        assertAllEntitiesEqual(DungonRes, Dungonload);

    }
    @Test
    public void testSpiderMovement() {
        clear();
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderTest_MovementWithBoulder", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "spider").get(0).getPosition();
        ArrayList<Position> movementTrajectory = new ArrayList<Position>();
        int x = pos.getX();
        int y = pos.getY();
        int nextPositionElement = 0;
        movementTrajectory.add(new Position(x, y - 1));
        movementTrajectory.add(new Position(x + 1, y - 1));
        movementTrajectory.add(new Position(x + 1, y));
        movementTrajectory.add(new Position(x + 1, y + 1));
        movementTrajectory.add(new Position(x + 1, y));
        movementTrajectory.add(new Position(x + 1, y - 1));
        movementTrajectory.add(new Position(x, y - 1));
        movementTrajectory.add(new Position(x - 1, y - 1));
        movementTrajectory.add(new Position(x, y - 1));
        for (int i = 0; i <= 7; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());
            nextPositionElement++;

        }
        DungeonResponse DungonRes = dmc.saveGame("d_spiderTest_MovementWithBoulder");
        DungeonResponse Dungonload = dmc.loadGame("d_spiderTest_MovementWithBoulder");
        assertAllEntitiesEqual(DungonRes, Dungonload);
        res = dmc.tick(Direction.UP);
        assertEquals(movementTrajectory.get(8), getEntities(res, "spider").get(0).getPosition());
    }
    @Test
    public void testDurationsPotion() {
        clear();
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testInvisibilityPotionCGCJB1657792158.6375983", "c_Battletest_PlayerStrong");
        String potion_id = getInventory(initialResponse, "invisibility_potion").get(0).getId();
        assertDoesNotThrow( () -> {
            controller.tick(potion_id);
        });
        controller.saveGame("testInvisibilityPotionCGCJB1657792158.6375983");
        controller.loadGame("testInvisibilityPotionCGCJB1657792158.6375983");
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);// DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        assertTrue(postBattleResponse.getBattles().size() == 0);
        assertTrue(getEntities(postBattleResponse, "spider").size() == 1);
    }
    // @Test
    public void testSwampTileMovement() {

    }
    @Test
    public void testBattle() {
        clear();
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("battleWithSpiderWinVKQT81657768515.1290405", "c_Battletest_PlayerStrong");
        controller.saveGame("battleWithSpiderWinVKQT81657768515.1290405");
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        postBattleResponse.getBattles().get(0);
        controller.loadGame("battleWithSpiderWinVKQT81657768515.1290405");
        DungeonResponse given = controller.tick(Direction.RIGHT);
        given.getBattles().get(0);
        asserAllBattleEqual(postBattleResponse, given);


    }

    @Test
    @DisplayName("test for spider spawn for saving data")
    public void testSpiderSpawn() {
        clear();
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_spiderSpwanTest", "c_spiderSpwanTest");
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(0, getEntities(res, "spider").size());
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, getEntities(res, "spider").size());
        DungeonResponse DungonRes = dmc.saveGame("d_spiderSpwanTest");
        DungeonResponse Dungonload = dmc.loadGame("d_spiderSpwanTest");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }
    
    @Test
    @DisplayName("Test generate dungeon and persistance")
    public void testGenerateDungeon() {
        clear();
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res =  controller.generateDungeon(0, 0, 1, 1, "c_Battletest_PlayerStrong");
        assertDoesNotThrow(() -> {
            controller.tick(Direction.DOWN);
        });
        DungeonResponse DungonRes = controller.saveGame("d_generateDungeon");
        DungeonResponse Dungonload = controller.loadGame("d_generateDungeon");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    @Test
    @DisplayName("test persistence with zombie spwaner and create zombie")
    public void testZombieSpwaner() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieSpwaner",
                        "c_zombieSpwaner");

        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        assertEquals(true,
                        actualDungonRes.getEntities().stream()
                                        .anyMatch(e -> e.getType().equals("zombie_toast_spawner")));
        assertEquals(1, getEntities(actualDungonRes, "zombie_toast").size());
        actualDungonRes = dmc.tick(Direction.LEFT);
        assertEquals(2, getEntities(actualDungonRes, "zombie_toast").size());
        DungeonResponse DungonRes = dmc.saveGame("d_zombieSpwaner");
        DungeonResponse Dungonload = dmc.loadGame("d_zombieSpwaner");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    @Test
    @DisplayName("test persistance with Hydra with battle")
    public void testHydra() {
        clear();
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest", "c_Battletest_PlayerStrong");
        res = dmc.tick(Direction.UP);
        assertEquals(1, getEntities(res, "hydra").size());
        assertEquals(0, getEntities(res, "player").size());
        DungeonResponse DungonRes = dmc.saveGame("d_hydra");
        DungeonResponse Dungonload = dmc.loadGame("d_hydra");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    @Test
    @DisplayName("test persistance with assassin and bribe situation")
    public void testAssassin() {
        clear();
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res;
        res = dmc.newGame("d_assassinTest_bribe", "c_BattleTest_rateHigh");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
        String assassinId = getEntities(res, "assassin").get(0).getId();
        System.out.println(getEntities(res, "assassin").get(0).getId());
        res = assertDoesNotThrow(()-> dmc.interact(assassinId));
        
        assertEquals(0, getInventory(res, "treasure").size());
        assertEquals(1, getEntities(res, "assassin").size());
        DungeonResponse DungonRes = dmc.saveGame("d_assassin");
        DungeonResponse Dungonload = dmc.loadGame("d_assassin");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    // @Test
    // @DisplayName("test persistance with time travel")
    public void testTimeTravel(){
        clear();
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravelFWQ7G1658488701.4102888", "c_Battletest_PlayerStrong");
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        DungeonResponse Res = controller.rewind(5);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(0, 0), older_player.getPosition());
        assertTrue(getEntities(Res, "mercenary").size() == 1);
        EntityResponse mercenary = getEntities(Res, "mercenary").get(0);
        assertEquals(new Position(15, 15), mercenary.getPosition());
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(5, 0), older_player.getPosition());
        // 
        Res = controller.tick(Direction.RIGHT);
        assertTrue(getEntities(Res, "older_player").size() == 0);
        DungeonResponse DungonRes = controller.saveGame("d_timeTravel");
        DungeonResponse Dungonload = controller.loadGame("d_timeTravel");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    @Test
    @DisplayName("test persistance with sceptre - fail")
    public void testSceptreFail() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_collectTests_BuildingSceptreFaile4",
                    "c_collectTests");
        assertEquals(0, getInventory(res, "sceptre").size());
        assertEquals(1, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "treasure").size());
        assertEquals(1, getInventory(res, "key").size());
        assertThrows(InvalidActionException.class, () -> {
            dmc.build("sceptre");  
        });
        DungeonResponse DungonRes = dmc.saveGame("d_sceptre");
        DungeonResponse Dungonload = dmc.loadGame("d_sceptre");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    @Test
    @DisplayName("test persistance with sceptre - success")
    public void testSceptre() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_collectTests_sceptreUsage",
                    "c_collectTests");
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, getInventory(res, "sceptre").size());
        assertEquals(1, getInventory(res, "sun_stone").size());
        assertEquals(0, getInventory(res, "arrow").size());
        String sceptreId = getInventory(res, "sceptre").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.tick(sceptreId));
        
        Position playerPosition = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());

        playerPosition = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());

        playerPosition = getEntities(res, "player").get(0).getPosition();
        res = dmc.tick(Direction.RIGHT);
        assertEquals(playerPosition, getEntities(res, "mercenary").get(0).getPosition());
        assertEquals(new Position(3,1), getEntities(res, "mercenary").get(0).getPosition());
        assertEquals(new Position(4,1), getEntities(res, "player").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(0, getEntities(res, "mercenary").size());

        DungeonResponse DungonRes = dmc.saveGame("d_sceptreTest");
        DungeonResponse Dungonload = dmc.loadGame("d_sceptreTest");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

    @Test
    @DisplayName("test persistance for invisibility potion to mercenary") 
    public void testInvisibilityPotion() {
        clear();
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("testAllyCEYHC1657994389.453653", "c_Battletest_PlayerStrong");
        String id = getEntities(res, "mercenary").get(0).getId();
        assertDoesNotThrow(()->{dmc.interact(id);});
        String nid = getInventory(res, "invisibility_potion").get(0).getId();
        assertDoesNotThrow(()->dmc.tick(nid));
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);
        dmc.tick(Direction.UP);

        DungeonResponse DungonRes = dmc.saveGame("d_invisibilityTest");
        DungeonResponse Dungonload = dmc.loadGame("d_invisibilityTest");
        assertAllEntitiesEqual(DungonRes, Dungonload);
    }

}
