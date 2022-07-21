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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PersistenceTests {
    @Test
    public void testCanSave() {
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
        DungeonManiaController dmc = new DungeonManiaController();
        dmc.newGame("d_movementTest_testMovementDown",
                "c_movementTest_testMovementDown");
        dmc.saveGame("d_movementTest_testMovementDown");
        dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_movementTest_testMovementDown");
        dmc.saveGame("d_collectTests_pickUpAllCollectableEntity");
        assertEquals(Arrays.asList("d_movementTest_testMovementDown", "d_movementTest_testMovementDown"),
                    dmc.allGames());
    }

    @Test
    public void testGoals() {

    }
    @Test
    public void testEntities() {

    }
    @Test
    public void testPlayerInventory() {

    }
    @Test
    public void testConfigs() {

    }
    
    @Test
    public void testPlayerMovement() {

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
