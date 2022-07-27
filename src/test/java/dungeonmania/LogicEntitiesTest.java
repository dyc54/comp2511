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
public class LogicEntitiesTest {
    @Test
    public void testLightBulbActive() {
        // player 
        // boulde
        // switch
        // light_ or
        // testLightBulbActive5S6TS1658822414.2938757
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLightBulbActive5S6TS1658822414.2938757", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        DungeonResponse res = dmc.tick(Direction.DOWN);
        assertEquals(1, getEntities(res, "light_bulb_on").size());

    }
    @Test
    public void testSwitchDoorActive() {
        // player 
        // boulde
        // switch
        // switch_door or
        // testSwitchDoorActiveQDH6U1658822553.7445798
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testSwitchDoorActiveQDH6U1658822553.7445798", "c_Battletest_PlayerStrong");
        EntityResponse switch_door = getEntities(initialResponse, "switch_door").get(0);
        assertEquals("switch_door", switch_door.getType());
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.RIGHT);
        dmc.tick(Direction.DOWN);
        dmc.tick(Direction.LEFT);
        DungeonResponse res = dmc.tick(Direction.DOWN);
        EntityResponse player = getEntities(res, "player").get(0);
        assertEquals(new Position(0, 2), player.getPosition());
        // close the door
        dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(new Position(0, 2), player.getPosition());

    }
    @Test
    public void testSwitchDoorCanOpen() {
        // [   1] 
        // switch_door or
        // 1: player, key
        // testSwitchDoorCanOpenSVS1D1658822848.709044
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testSwitchDoorCanOpenSVS1D1658822848.709044", "c_Battletest_PlayerStrong");
        EntityResponse switch_door = getEntities(initialResponse, "switch_door").get(0);
        assertEquals("switch_door", switch_door.getType());
        dmc.tick(Direction.DOWN);
        DungeonResponse res = dmc.tick(Direction.DOWN);
        EntityResponse player = getEntities(res, "player").get(0);
        assertEquals(new Position(0, 2), player.getPosition());
    }
    @Test
    public void testActiveViaOneWire() {
        // player boulde switch  wire  light_bulb_or
        // testActiveViaOneWireH82241658823302.8742416
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testActiveViaOneWireH82241658823302.8742416", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_on").size());
    }
    @Test
    public void testActiveViaMutiWires() {
        // player boulde switch [   1] [    ] [    ] 
        // [    ] [    ] [    ]  wire  [    ] [    ]
        // [    ] [    ] [    ]  wire  [    ] [    ]
        // [    ] [    ] [    ]  wire   wire  light_ or
        // 1: wall, wire
        // testActiveViaMutiWiresYYFCU1658823557.3999043
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testActiveViaMutiWiresYYFCU1658823557.3999043", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_on").size());
    }
}
