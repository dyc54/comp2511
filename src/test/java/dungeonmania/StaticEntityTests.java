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

public class StaticEntityTests {
    @Test
    @DisplayName("Test the player cannot pass a wall")
    public void testWall() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_spiderTest_basicMovement",
                "c_spiderTest_basicMovement");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1),
                false);

        // move player upward
        DungeonResponse actualDungonRes = dmc.tick(Direction.UP);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move a boulder")
    public void testMoveBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_complexGoalsTest_andAll",
                "c_complexGoalsTest_andAll");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 1),
                false);

        DungeonResponse actualDungonRes;
        actualDungonRes = dmc.tick(Direction.RIGHT);
        actualDungonRes = dmc.tick(Direction.RIGHT);

        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert the position of boulder
        Position boulderPosition = getEntities(actualDungonRes, "boulder").get(0).getPosition();
        assertEquals(new Position(4, 1), boulderPosition);

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player cannot move a boulder if there are obstacle behind it")
    public void testObstacleBehindBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_moveBoulderTest",
                "c_moveBoulderTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 1),
                false);

        DungeonResponse actualDungonRes;
        actualDungonRes = dmc.tick(Direction.RIGHT);

        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can move through a portal")
    public void testMoveThroughPortal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_portalTest",
                "c_portalTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 3),
                false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.DOWN);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer.getPosition(), actualPlayer.getPosition());
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test the player can't move through a portal if there is a wall behind it")
    public void testObstacleBehindPortal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_portalTest",
                "c_portalTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(3, 0),
                false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer.getPosition(), actualPlayer.getPosition());
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test advanced portal")
    public void testAdvancedPortal() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("d_portalTest",
                "c_portalTest");
        EntityResponse initPlayer = getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(), initPlayer.getType(), new Position(1, 4),
                false);

        // move player downward
        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        EntityResponse actualPlayer = getPlayer(actualDungonRes).get();

        // assert after movement
        assertEquals(expectedPlayer.getPosition(), actualPlayer.getPosition());
        assertEquals(expectedPlayer, actualPlayer);
    }

    @Test
    @DisplayName("Test zombie toast spwaner")
    public void testZombieSpwaner() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieSpwaner",
                "c_zombieSpwaner");

        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        assertEquals(true,
                actualDungonRes.getEntities().stream().anyMatch(e -> e.getType().equals("zombie_toast_spawner")));
        assertEquals(1, getEntities(actualDungonRes, "zombie_toast").size());
        actualDungonRes = dmc.tick(Direction.LEFT);
        assertEquals(2, getEntities(actualDungonRes, "zombie_toast").size());
    }

    @Test
    @DisplayName("Test zombie toast spwaner cannot spawn zombie if there are obstacles at all the cardinally adjacent positions of it")
    public void testZombieSpwanerCannotSpwan() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieCannotSpwan",
                "c_zombieSpwanerCannotSpwan");

        DungeonResponse actualDungonRes = dmc.tick(Direction.LEFT);
        assertEquals(true,
                actualDungonRes.getEntities().stream().anyMatch(e -> e.getType().equals("zombie_toast_spawner")));
        assertEquals(0, getEntities(actualDungonRes, "zombie_toast").size());
    }

    @Test
    @DisplayName("Test player can use a key to open and walk through a door")
    public void useKeyWalkThroughOpenDoor() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_DoorsKeysTest_useKeyWalkThroughOpenDoor",
                "c_DoorsKeysTest_useKeyWalkThroughOpenDoor");

        // pick up key
        res = dmc.tick(Direction.RIGHT);
        Position pos = getEntities(res, "player").get(0).getPosition();
        assertEquals(1, getInventory(res, "key").size());

        // walk through door and check key is gone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getInventory(res, "key").size());
        assertNotEquals(pos, getEntities(res, "player").get(0).getPosition());
    }
}
