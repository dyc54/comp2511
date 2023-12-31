package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
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
    @Test
    public void testExpection() {
        DungeonManiaController controller = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> {
            controller.generateDungeon(0, 0, 1, 1, "xxxxxxxxxxx");
        });

    }
}
