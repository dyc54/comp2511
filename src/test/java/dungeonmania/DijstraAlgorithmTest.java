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
import dungeonmania.helpers.DijstraAlgorithm;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

public class DijstraAlgorithmTest {
    @Test
    @DisplayName("Test the player can pick up wood in map")
    public void testPickUpWood() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_testDijstraAlgorithm",
                "c_collectTests");
       
        DijstraAlgorithm da = dmc.testDijstraAlgorithm();
        da.dijstra();
    }
}
