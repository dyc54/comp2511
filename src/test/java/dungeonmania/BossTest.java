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
    public void HydraTestWin() {
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
    public void HydraMovement() {
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
    @DisplayName("Test Hydra random move")
    public void HydraTestLose() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_hydraTest", "c_BattleTest_hydra");
        res = dmc.tick(Direction.UP);
        assertEquals(0, getEntities(res, "hydra").size());
        assertEquals(1, getEntities(res, "player").size());
    }
    /*
    @Test
    @DisplayName("Test Hydra has certain chance to increase amount")
    public void HydraTestChance() {

    }
    */
}
