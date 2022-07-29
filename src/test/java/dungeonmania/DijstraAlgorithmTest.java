package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static dungeonmania.TestUtils.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.helpers.DijstraAlgorithm;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class DijstraAlgorithmTest {
    @Test
    @DisplayName("Test the Mercenary Chase player by dj")
    public void testMercenaryChasePlayer1() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_MercenaryChasePlayerByDJ","c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4,2), getEntities(res, "mercenary").get(0).getPosition());
          
    }

    @Test
    @DisplayName("Test the Mercenary Chase player by portal red")
    public void testMercenaryChasePlayerPortalAllRed() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_MercenaryChasePlayerByPortalRed","c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(0,5), getEntities(res, "mercenary").get(0).getPosition());
          
    }

    @Test
    @DisplayName("Test the Mercenary Chase player by portal blue")
    public void testMercenaryChasePlayerPortalAllblue() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_MercenaryChasePlayerByPortalBlue","c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4,2), getEntities(res, "mercenary").get(0).getPosition());
          
    }

    @Test
    @DisplayName("Test the Mercenary Chase player by portal fail")
    public void testMercenaryChasePlayerPortalAllbluered() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_MercenaryChasePlayerByPortalBlueRed","c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4,2), getEntities(res, "mercenary").get(0).getPosition());
          
    }

    @Test
    @DisplayName("Test the Mercenary Chase player by SW")
    public void testMercenaryChasePlayerSW() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_MercenaryChasePlayerBySW","c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2,4), getEntities(res, "mercenary").get(0).getPosition());
          
    }
}
