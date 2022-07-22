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
            assertEquals(expectEntity.getId(), givenEntity.getId());
            assertEquals(expectEntity.getPosition(), givenEntity.getPosition());
            assertEquals(expectEntity.getType(), givenEntity.getType());
        }
    }
    private void assertAllInventoryEqual(DungeonResponse expect, DungeonResponse Dungonload) {
        for (int i = 0; i < expect.getEntities().size(); i++) {
            ItemResponse expectItem = expect.getInventory().get(i);
            ItemResponse givenItem = Dungonload.getInventory().get(i);
            assertEquals(expectItem.getId(), givenItem.getId());
            assertEquals(expectItem.getType(), givenItem.getType());
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

    }

    @Test
    public void testZombieMovement() {

    }
    @Test
    public void testSpiderMovement() {

    }
    @Test
    public void testDurationsWeapon() {

    }
    @Test
    public void testDurationsPotion() {

    }
    // @Test
    public void testSwampTileMovement() {

    }
    @Test
    public void testBattle() {

    }

}
