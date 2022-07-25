package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static dungeonmania.TestUtils.getInventory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;


public class M3CollectAndBuildTest {
    public DungeonResponse playerMoveController(DungeonManiaController dmc, Direction direction, int movetime){
        DungeonResponse res = dmc.tick(direction);
        for (int i = 1; i < movetime; i++) {
            res = dmc.tick(direction);
        }
        return res;
    }

    /* **************************************************TEST PICK UP FUNCTION********************************************* */
    @Test
    @DisplayName("Test the player can pick up wood in map")
    public void testPickUpWood() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");
        // pick up wood
        res = playerMoveController(dmc, Direction.RIGHT, 1);
        assertEquals(1, getInventory(res, "wood").size());

        //pick up wood again
        res = playerMoveController(dmc, Direction.RIGHT, 2);
        assertEquals(2, getInventory(res, "wood").size());
    }
}
