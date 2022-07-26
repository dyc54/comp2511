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
    }
    @Test
    public void testSwitchDoorActive() {
        // player 
        // boulde
        // switch
        // switch_door or
        // testSwitchDoorActiveQDH6U1658822553.7445798
    }
    @Test
    public void testSwitchDoorCanOpen() {
        // [   1] 
        // switch_door or
        // 1: player, key
        // testSwitchDoorCanOpenSVS1D1658822848.709044
    }
    @Test
    public void testActiveViaOneWire() {
        // player boulde switch  wire  light_bulb_or
        // testActiveViaOneWireH82241658823302.8742416
    }
    @Test
    public void testActiveViaMutiWires() {
        // player boulde switch [   1] [    ] [    ] 
        // [    ] [    ] [    ]  wire  [    ] [    ]
        // [    ] [    ] [    ]  wire  [    ] [    ]
        // [    ] [    ] [    ]  wire   wire  light_ or
        // 1: wall, wire
        // testActiveViaMutiWiresYYFCU1658823557.3999043
    }
}
