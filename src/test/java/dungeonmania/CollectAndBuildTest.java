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

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class CollectAndBuildTest {
    
    @Test
    @DisplayName("Test the player can pick up wood in map")
    public void testPickUpWood() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");
        // pick up wood
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "wood").size());

        //pick up wood again
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
    }


    @Test
    @DisplayName("Test the player can pick arrow in map")
    public void testPickUpArrow() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        //pick up arrow
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(1, getInventory(res, "arrow").size());

        //pick up wood again
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        assertEquals(2, getInventory(res, "arrow").size());
    }
    

    @Test
    @DisplayName("Test the player can pick invincibility_potion in map")
    public void testPickUpInvincibilityPotion() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "invincibility_potion").size());
    }
    

    @Test
    @DisplayName("Test the player can pick invisibility_potion in map")
    public void testPickUpInvisibilityPotion() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "invisibility_potion").size());
    }

    @Test
    @DisplayName("Test the player can pick key map")
    public void testPickUpkey() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "key").size());
    }


    @Test
    @DisplayName("Test the player can pick treasure map")
    public void testPickUpTreasure() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "treasure").size());
    }


    @Test
    @DisplayName("Test the player can pick sword map")
    public void testPickUpSword() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getInventory(res, "sword").size());
    }

    @Test
    @DisplayName("Test the player can bulid bow ")
    public void testBulidBow() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.build("bow");
        assertEquals(1, getInventory(res, "bow").size());
        assertEquals(1, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "arrow").size());

    }

    @Test
    @DisplayName("Test the player can bulid shield ")
    public void testBulidShield() throws IllegalArgumentException, InvalidActionException {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_collectTests_pickUpAllCollectableEntity",
                "c_collectTests");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, getInventory(res, "wood").size());
        /* res = dmc.build("shield");
        assertEquals(1, getInventory(res, "shield").size());
        assertEquals(0, getInventory(res, "wood").size());
        assertEquals(0, getInventory(res, "treasure").size()); */

    }
}
