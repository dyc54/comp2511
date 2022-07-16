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


public class CollectAndBuildTest {

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


    @Test
    @DisplayName("Test the player can pick arrow in map")
    public void testPickUpArrow() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        //pick up arrow
        res = playerMoveController(dmc, Direction.RIGHT, 3);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "arrow").size());

        //pick up wood again
        res = playerMoveController(dmc, Direction.RIGHT, 4);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(2, getInventory(res, "arrow").size());
    }
    

    @Test
    @DisplayName("Test the player can pick invincibility_potion in map")
    public void testPickUpInvincibilityPotion() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = playerMoveController(dmc, Direction.RIGHT, 5);
        assertEquals(1, getInventory(res, "invincibility_potion").size());
    }
    

    @Test
    @DisplayName("Test the player can pick invisibility_potion in map")
    public void testPickUpInvisibilityPotion() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = playerMoveController(dmc, Direction.RIGHT, 6);
        assertEquals(1, getInventory(res, "invisibility_potion").size());
    }

    @Test
    @DisplayName("Test the player can pick key map")
    public void testPickUpkey() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = playerMoveController(dmc, Direction.RIGHT, 7);
        assertEquals(1, getInventory(res, "key").size());
    }


    @Test
    @DisplayName("Test the player can pick treasure map")
    public void testPickUpTreasure() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = playerMoveController(dmc, Direction.RIGHT, 8);
        assertEquals(1, getInventory(res, "treasure").size());
    }


    @Test
    @DisplayName("Test the player can pick sword map")
    public void testPickUpSword() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = playerMoveController(dmc, Direction.RIGHT, 9);
        assertEquals(1, getInventory(res, "sword").size());
    }

/* **************************************************TEST BUILD FUNCTION********************************************* */
    @Test
    @DisplayName("Test the player can build bow fail by wrong argument")
    public void testBulidBowFail() throws IllegalArgumentException, InvalidActionException {
        assertThrows(IllegalArgumentException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                    "c_collectTests");

            res = playerMoveController(dmc, Direction.RIGHT, 10);
            // before build bow
            assertEquals(0, getInventory(res, "bow").size());
            assertEquals(2, getInventory(res, "wood").size());
            assertEquals(3, getInventory(res, "arrow").size());

            // after build bow
            res = dmc.build(new String("bows"));
            assertEquals(0, getInventory(res, "bow").size());
            assertEquals(2, getInventory(res, "wood").size());
            assertEquals(3, getInventory(res, "arrow").size());


            List<String> buildables = new ArrayList<>();
            assertEquals(buildables, res.getBuildables());
        });
    }


    @Test
    @DisplayName("Test the player can build bow fail by no enough material")
    public void testBulidBowFail2() throws IllegalArgumentException, InvalidActionException {
        // after build bow
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();
        
            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
            "c_collectTests");
            
            res = playerMoveController(dmc, Direction.RIGHT, 1);
            // before build bow
            assertEquals(0, getInventory(res, "bow").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(0, getInventory(res, "arrow").size());
            res = dmc.build("bow");
        });


    }

    @Test
    @DisplayName("Test the player can build bow ")
    public void testBulidBow() throws IllegalArgumentException, InvalidActionException {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                    "c_collectTests");

            res = playerMoveController(dmc, Direction.RIGHT, 10);
            // before build bow
            assertEquals(0, getInventory(res, "bow").size());
            assertEquals(2, getInventory(res, "wood").size());
            assertEquals(3, getInventory(res, "arrow").size());

            // after build bow
            res = dmc.build("bow");
            assertEquals(1, getInventory(res, "bow").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(0, getInventory(res, "arrow").size());


            List<String> buildables = new ArrayList<>();
            // buildables.add("bow");
            assertEquals(buildables, res.getBuildables());
           
        });

    }

    @Test
    @DisplayName("Test the player can build shield fail by wrong argument")
    public void testBulidShieldByTreasureFail() throws IllegalArgumentException, InvalidActionException {
        assertThrows(IllegalArgumentException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                    "c_collectTests");

            res = playerMoveController(dmc, Direction.RIGHT, 10);
            // before build shield by wood and treasure 
            assertEquals(0, getInventory(res, "shield").size());
            assertEquals(2, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "treasure").size());

            // after build shield by wood and treasure
            res = dmc.build("shieldss");
            assertEquals(0, getInventory(res, "shield").size());
            assertEquals(2, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "treasure").size());

            List<String> buildables = new ArrayList<>();
            assertEquals(buildables, res.getBuildables());
        });
    }


    @Test
    @DisplayName("Test the player can build shield fail by no enough material")
    public void testBulidShieldByTreasureFail2() throws IllegalArgumentException, InvalidActionException {
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                    "c_collectTests");

            res = playerMoveController(dmc, Direction.RIGHT, 1);
            // before build shield by wood and treasure 
            assertEquals(0, getInventory(res, "shield").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(0, getInventory(res, "treasure").size());

            // after build shield by wood and treasure
            res = dmc.build("shield");
            assertEquals(0, getInventory(res, "shield").size());
            assertEquals(1, getInventory(res, "wood").size());
            assertEquals(0, getInventory(res, "treasure").size());

            List<String> buildables = new ArrayList<>();
            assertEquals(buildables, res.getBuildables());
        });
    }

    @Test
    @DisplayName("Test the player can build shield by wood and key")
    public void testBulidShieldByKey() throws IllegalArgumentException, InvalidActionException {

        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                    "c_collectTests");

            res = playerMoveController(dmc, Direction.RIGHT, 12);
            // before build shield by wood and treasure 
            assertEquals(0, getInventory(res, "shield").size());
            assertEquals(4, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "treasure").size());
            assertEquals(1, getInventory(res, "key").size());

            // after build 2 shield by wood and treasure or key
            assertEquals(Arrays.asList("bow", "shield"), res.getBuildables());
            res = dmc.build("bow");
            assertEquals(Arrays.asList("shield"), res.getBuildables());
            res = dmc.build("shield");
            assertEquals(1, getInventory(res, "shield").size());
            assertEquals(1, getInventory(res, "treasure").size());
            assertEquals(0, getInventory(res, "key").size());
            assertEquals(1, getInventory(res, "wood").size());

            List<String> buildables = new ArrayList<>();
            assertEquals(buildables, res.getBuildables());
        });
    }

    @Test
    @DisplayName("Test the player can build shield fail by no enough material")
    public void testBulidShieldByKeyFail() throws IllegalArgumentException, InvalidActionException {
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                    "c_collectTests");

            res = playerMoveController(dmc, Direction.RIGHT, 14);
            // before build shield by wood and treasure 
            assertEquals(0, getInventory(res, "shield").size());
            assertEquals(6, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "treasure").size());
            assertEquals(Arrays.asList( "bow", "shield"), res.getBuildables());

            res = dmc.build("shield");
            res = dmc.build("shield");
            res = dmc.build("shield");

            List<String> buildables = new ArrayList<>();
            assertEquals(buildables, res.getBuildables());

            // build shield again
            res = dmc.build("shield");
            assertEquals(0, getInventory(res, "key").size());
            assertEquals(0, getInventory(res, "treasure").size());
            assertEquals(2, getInventory(res, "wood").size());
            assertEquals(buildables, res.getBuildables());
        });
    }

    @Test
    @DisplayName("Test the player can build shield and bow and pick up all item in map")
    public void testBulidShieldAndBow() throws IllegalArgumentException, InvalidActionException {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                    "c_collectTests");

            res = playerMoveController(dmc, Direction.RIGHT, 14);
            List<String> buildables = new ArrayList<>();
            // pick up all item
            assertEquals(0, getInventory(res, "shield").size());
            assertEquals(0, getInventory(res, "bow").size());
            assertEquals(6, getInventory(res, "wood").size());
            assertEquals(1, getInventory(res, "treasure").size());
            assertEquals(1, getInventory(res, "key").size());
            assertEquals(1, getInventory(res, "invincibility_potion").size());
            assertEquals(1, getInventory(res, "invisibility_potion").size());
            assertEquals(1, getInventory(res, "sword").size());
            assertEquals(3, getInventory(res, "arrow").size());
            assertEquals(Arrays.asList("bow", "shield"), res.getBuildables());

            // after build shield by wood and treasure
            res = dmc.build("shield");
            res = dmc.build("shield");
            res = dmc.build("bow");
            assertEquals(new ArrayList<>(), res.getBuildables());
        });
    }

}
