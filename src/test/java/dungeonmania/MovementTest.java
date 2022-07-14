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
public class MovementTest {
    @Test
    @DisplayName("Test zombie toast random movement")
    public void zombieMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_zombieTest_randomMove", "c_spiderTest_basicMovement");

        for (int i = 0; i <= 20; i++) {
            res = dmc.tick(Direction.DOWN);
            System.out.println(getEntities(res, "zombie_toast").get(0).getPosition());
            assertNotEquals(new Position(5,4), getEntities(res, "zombie_toast").get(0).getPosition());
            assertNotEquals(new Position(5,6), getEntities(res, "zombie_toast").get(0).getPosition());
            assertNotEquals(new Position(6,5), getEntities(res, "zombie_toast").get(0).getPosition());
        }
        
    }

    @Test
    @DisplayName("Test movement of spiders with boulder")
    public void spiderMovementWithBoulder() {
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
        for (int i = 0; i <= 8; ++i) {
            res = dmc.tick(Direction.UP);
            assertEquals(movementTrajectory.get(nextPositionElement), getEntities(res, "spider").get(0).getPosition());

            nextPositionElement++;

        }
    }

    // @Test
    @DisplayName("The movement for mercenary when they are not bribed") 
    public void mercenaryMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        int x = pos.getX();
        int y = pos.getY();
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(x - 1, y), getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(x - 1, y - 1), getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(x - 2, y - 1), getEntities(res, "mercenary").get(0).getPosition());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(x - 2, y - 2), getEntities(res, "mercenary").get(0).getPosition());
    }

    @Test
    @DisplayName("The movement for mercenary with wall when they are not bribed") 
    public void mercenaryWithWall() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_wall", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "mercenary").get(0).getPosition();
        int x = pos.getX();
        int y = pos.getY();
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(x, y + 1), getEntities(res, "mercenary").get(0).getPosition());
    }

    // @Test
    @DisplayName("The following state for mercenary movement")
    public void mercenaryFollowing() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_follow", "c_spiderTest_basicMovement");
        Position pos = getEntities(res, "player").get(0).getPosition();
        int x = pos.getX();
        int y = pos.getY();
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(x, y), getEntities(res, "ally").get(0).getPosition());
    }

    @Test
    @DisplayName("The mercenary is bribed by the player-success")
    public void mercenaryBribe() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res;
        res = dmc.newGame("d_mercenaryTest_bribe", "c_spiderTest_basicMovement");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
        String mercenaryId = getEntities(res, "mercenary").get(0).getId();
        res = assertDoesNotThrow(()-> dmc.interact(mercenaryId));
        
        assertEquals(0, getInventory(res, "treasure").size());
        assertEquals(1, getEntities(res, "ally").size());
    }
    
    // @Test
    @DisplayName("The mercenary is bribed by the player-fail because player has not enough treasure")
    public void mercenaryBirbeFail1() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_bribeFail", "c_mercenaryTest_bribeFail");
        res = dmc.tick(Direction.UP);
        assertEquals(0, getInventory(res, "treasure").size());
        String mercenaryId = getEntities(res, "mercenary").get(0).getId();
        assertThrows(InvalidActionException.class, ()-> {
            dmc.interact(mercenaryId);
        });
        
    }

    @Test
    @DisplayName("The mercenary is bribed by the player-fail because mercenary is not in the bribed radius")
    public void mercenaryBirbeFail2() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_mercenaryTest_bribeFail2", "c_mercenaryTest_bribeFail");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
        String mercenaryId = getEntities(res, "mercenary").get(0).getId();
        assertThrows(InvalidActionException.class, ()-> dmc.interact(mercenaryId));
        assertThrows(IllegalArgumentException.class, ()->dmc.interact("123"));
        
    }
}
