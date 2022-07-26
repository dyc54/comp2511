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

public class BossTest {
    @Test
    @DisplayName("Hydra battle with player - win")
    public void testHydraWin() {
        /*     [W] [W]
         * [W] [P] [H] [W]
         *     [W] [W]
         */    
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest", "c_Battletest_PlayerStrong");
        res = dmc.tick(Direction.UP);
        assertEquals(1, getEntities(res, "hydra").size());
        assertEquals(0, getEntities(res, "player").size());
    }

    @Test
    @DisplayName("Test Hydra random move")
    public void testHydraMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest_move", "c_Battletest_PlayerStrong");
        for (int i = 0; i < 20; i++) {
            res = dmc.tick(Direction.UP);
            System.out.println("-----------------"+ getEntities(res, "hydra").get(0).getPosition());
            assertNotEquals(new Position(5, 4), getEntities(res, "hydra").get(0).getPosition());
            assertNotEquals(new Position(5, 6), getEntities(res, "hydra").get(0).getPosition());
            assertNotEquals(new Position(6, 5), getEntities(res, "hydra").get(0).getPosition());
        }
        
    }

    @Test
    @DisplayName("Test hydra battle with player - lose")
    public void testHydraLose() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest", "c_BattleTest_hydra");
        res = dmc.tick(Direction.UP);
        assertEquals(0, getEntities(res, "hydra").size());
        assertEquals(1, getEntities(res, "player").size());
    }
    
    @Test
    @DisplayName("Test Hydra has certain chance to increase amount - low increase rate")
    public void testHydraChanceLowRate() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest", "c_BattleTest_rateLow");
        res = dmc.tick(Direction.UP);
        assertEquals(0, getEntities(res, "hydra").size());
        assertEquals(1, getEntities(res, "player").size());
    }
    
    @Test
    @DisplayName("Test Hydra has certain chance to increase amount - High increase rate")
    public void testHydraChanceHighRate() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest", "c_BattleTest_rateHigh");
        res = dmc.tick(Direction.UP);
        assertEquals(1, getEntities(res, "hydra").size());
        assertEquals(0, getEntities(res, "player").size());
    }

    @Test
    @DisplayName("Test assassin chase the player")
    public void assassinMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_move", "c_BattleTest_rateHigh");
        Position pos = getEntities(res, "assassin").get(0).getPosition();
        int x = pos.getX();
        int y = pos.getY();
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(x - 1, y), getEntities(res, "assassin").get(0).getPosition());
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(x - 1, y - 1), getEntities(res, "assassin").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(x - 2, y - 1), getEntities(res, "assassin").get(0).getPosition());
    }

    @Test
    @DisplayName("The movement for assassin with wall when they are not bribed") 
    public void testAssassinWithWall() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_assassinTest_wall", "c_BattleTest_rateHigh");
        Position pos = getEntities(res, "assassin").get(0).getPosition();
        int x = pos.getX();
        int y = pos.getY();
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(x, y + 1), getEntities(res, "assassin").get(0).getPosition());
    }

    @Test
    @DisplayName("The assassin is bribed by the player-success, rate is 1")
    public void assassinBribe() {
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
    }

    @Test
    @DisplayName("The assassin is bribed by the player-fail, rate is 0")
    public void assassinBribeFail() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res;
        res = dmc.newGame("d_assassinTest_bribe", "c_BattleTest_rateLow");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
        String assassinId = getEntities(res, "assassin").get(0).getId();
        System.out.println(getEntities(res, "assassin").get(0).getId());
        assertThrows(InvalidActionException.class, ()-> dmc.interact(assassinId));
    }

    @Test
    @DisplayName("Test the Invisibility potion in assassin recon radius")
    public void assassinChaseUsingInvisibility() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res;
        res = dmc.newGame("d_assassinTest_invisibility", "c_BattleTest_rateHigh");
        String id = getInventory(res, "invisibility_potion").get(0).getId();
        res = assertDoesNotThrow(()-> dmc.tick(id));
        assertEquals(new Position(3, 1), getEntities(res, "assassin").get(0).getPosition());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2, 1), getEntities(res, "assassin").get(0).getPosition());
    }

    @Test
    @DisplayName("Test the Invisibility potion out of assassin recon radius, so random movement")
    public void assassinChaseUsingInvisibilityRandom() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res;
        res = dmc.newGame("d_assassinTest_outofRadius", "c_BattleTest_rateHigh");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 1), getEntities(res, "assassin").get(0).getPosition());
        String id = getInventory(res, "invisibility_potion").get(0).getId();
        res = assertDoesNotThrow(()-> dmc.tick(id));
        assertEquals(new Position(7, 2) , getEntities(res, "assassin").get(0).getPosition());
    }

}
