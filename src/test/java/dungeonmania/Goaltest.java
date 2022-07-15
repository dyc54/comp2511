package dungeonmania;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Goaltest {
    // @Test
    public void testSingleGoalExit() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("24P4BWCXL31657541510.444106",
                "c_spiderTest_basicMovement");
        assertTrue(getGoals(initDungonRes).contains(":exit"));
        assertFalse(getGoals(initDungonRes).contains(":treasure"));
        assertFalse(getGoals(initDungonRes).contains(":boulders"));
        assertFalse(getGoals(initDungonRes).contains(":enemies"));
    }

    // @Test
    public void testSingleGoalEnemies() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("LPR3OCI00B1657541807.9557867",
                "c_spiderTest_basicMovement");
        assertFalse(getGoals(initDungonRes).contains(":exit"));
        assertFalse(getGoals(initDungonRes).contains(":treasure"));
        assertFalse(getGoals(initDungonRes).contains(":boulders"));
        assertTrue(getGoals(initDungonRes).contains(":enemies"));
    }

    // @Test
    public void testSingleGoalBoulder() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("QMYNAP8EE81657541856.4180584",
                "c_spiderTest_basicMovement");
        assertFalse(getGoals(initDungonRes).contains(":exit"));
        assertFalse(getGoals(initDungonRes).contains(":treasure"));
        assertTrue(getGoals(initDungonRes).contains(":boulders"));
        assertFalse(getGoals(initDungonRes).contains(":enemies"));
    }

    // @Test
    public void testSingleGoalTreasure() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("35DSX86O451657542045.7539003",
                "c_spiderTest_basicMovement");
        assertFalse(getGoals(initDungonRes).contains(":exit"));
        assertTrue(getGoals(initDungonRes).contains(":treasure"));
        assertFalse(getGoals(initDungonRes).contains(":boulders"));
        assertFalse(getGoals(initDungonRes).contains(":enemies"));
    }

    // @Test
    public void testTwoGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("989JV0U2M91657542060.802809",
                "c_spiderTest_basicMovement");
        assertTrue(getGoals(initDungonRes).contains(":exit"));
        assertTrue(getGoals(initDungonRes).contains(":treasure"));
        assertFalse(getGoals(initDungonRes).contains(":boulders"));
        assertFalse(getGoals(initDungonRes).contains(":enemies"));
    }

    // @Test
    public void testThreeGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("RE16RA3HND1657542126.6908503",
                "c_spiderTest_basicMovement");
        assertTrue(getGoals(initDungonRes).contains(":exit"));
        assertTrue(getGoals(initDungonRes).contains(":treasure"));
        assertTrue(getGoals(initDungonRes).contains(":boulders"));
        assertFalse(getGoals(initDungonRes).contains(":enemies"));
    }

    // @Test
    public void testFourGoals() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame("RE16RA3HND1657542126.6908503",
                "c_spiderTest_basicMovement");
        assertTrue(getGoals(initDungonRes).contains(":exit"));
        assertTrue(getGoals(initDungonRes).contains(":treasure"));
        assertTrue(getGoals(initDungonRes).contains(":boulders"));
        assertTrue(getGoals(initDungonRes).contains(":enemies"));
    }
}
