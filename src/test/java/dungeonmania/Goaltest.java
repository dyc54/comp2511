package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getGoals;

import org.junit.jupiter.api.Test;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;


public class Goaltest {
    @Test
    public void testWalkThroughExit() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_andAll", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // kill spider
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // pickup treasure
        res = dmc.tick(Direction.DOWN);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move to exit
        res = dmc.tick(Direction.DOWN);
        assertEquals("", getGoals(res));
        assertFalse(getGoals(res).contains(":exit"));

        // move to next place
        res = dmc.tick(Direction.DOWN);
        assertTrue(getGoals(res).contains(":exit"));
        assertFalse(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));
    }

    @Test
    public void testGoalMoveBoulderOutOfSwitch() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_complexGoalsTest_andAll", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertTrue(getGoals(res).contains(":enemies"));

        // kill spider
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // move boulder onto switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertFalse(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));

        // Push the boulder to leave the switch
        res = dmc.tick(Direction.RIGHT);
        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":treasure"));
        assertTrue(getGoals(res).contains(":boulders"));
        assertFalse(getGoals(res).contains(":enemies"));
    }

    @Test
    public void testGoalOR() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_goalTest_OR", "c_complexGoalsTest_andAll");

        assertTrue(getGoals(res).contains(":exit"));
        assertTrue(getGoals(res).contains(":enemies"));

        // kill spider
        res = dmc.tick(Direction.RIGHT);

        assertEquals("", getGoals(res));
    }

    @Test
    public void testGoalDestoryZombieSpwaner() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_goalTestDestorySpwaner", "c_goalTestDestorySpwaner");
        res = dmc.tick(Direction.DOWN); // pick up sword
        assertEquals(1, getInventory(res, "sword").size());
        assertEquals(1, getEntities(res, "zombie_toast_spawner").size());
        String spawnerId = getEntities(res, "zombie_toast_spawner").get(0).getId();
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));
        assertEquals(0, getEntities(res, "zombie_toast_spawner").size());
        assertEquals(0, getEntities(res, "zombie").size());
        assertEquals("", getGoals(res));
    }

}
