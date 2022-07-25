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
import org.reflections.vfs.Vfs.Dir;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
public class testGenerateDungeon {
    @Test
    public void testSimplyMap() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res =  controller.generateDungeon(0, 0, 15, 15, "c_Battletest_PlayerStrong");
        assertEquals(1, getEntities(res, "exit").size());
        assertEquals(1, getEntities(res, "player").size());
    }
    @Test
    public void testPositions() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res =  controller.generateDungeon(0, 0, 15, 15, "c_Battletest_PlayerStrong");
        assertEquals(1, getEntities(res, "exit").size());
        assertEquals(1, getEntities(res, "player").size());
        assertEquals(new Position(0, 0), getEntities(res, "player").get(0).getPosition());
        assertEquals(new Position(15, 15), getEntities(res, "exit").get(0).getPosition());
    }
    @Test
    public void testEdgeCaseLarge() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res =  controller.generateDungeon(0, 0, 1, 10, "c_Battletest_PlayerStrong");
        assertEquals(1, getEntities(res, "exit").size());
        assertEquals(1, getEntities(res, "player").size());
        assertEquals(new Position(0, 0), getEntities(res, "player").get(0).getPosition());
        assertEquals(new Position(1, 10), getEntities(res, "exit").get(0).getPosition());

    }
    @Test
    public void testEdgeCaseSmall() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res =  controller.generateDungeon(0, 0, 1, 1, "c_Battletest_PlayerStrong");
        assertEquals(1, getEntities(res, "exit").size());
        assertEquals(1, getEntities(res, "player").size());
        assertEquals(new Position(0, 0), getEntities(res, "player").get(0).getPosition());
        assertEquals(new Position(1, 1), getEntities(res, "exit").get(0).getPosition());

    }
    @Test
    public void testPlayerMovement() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res =  controller.generateDungeon(0, 0, 1, 1, "c_Battletest_PlayerStrong");
        assertDoesNotThrow(() -> {
            controller.tick(Direction.DOWN);
        });
    }
    @Test
    public void testGoal() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse res =  controller.generateDungeon(0, 0, 1, 1, "c_Battletest_PlayerStrong");
        assertTrue(res.getGoals().contains(":exit"));
        
    }
}
