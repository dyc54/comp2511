package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertEquals;



import static dungeonmania.TestUtils.getEntities;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
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
    public void testSwitchDoorCanReclose() {
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
    @Test
    @DisplayName("Test  activate an entity with logic AND , which is adjacent  with two logical entities")
    public void testLogicAnd1() {
        // player boulde switch light_off_AND 
        // [    ] [    ] boulde switch
        // testLogicAndK8PNH1658928902.046343
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLogicAndK8PNH1658928902.046343", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        
        // active 1 switch -> bulb is not activated.
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());

        // active 2 switch -> bulb is activated.
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, getEntities(res, "light_bulb_off").size());
        assertEquals(1, getEntities(res, "light_bulb_on").size());

        // deactive 1 switch -> bulb is not activated
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());

    }

    @Test
    @DisplayName("Test activate a entity with logic AND, which is adjacent  with three logical entities")
    public void testLogicAnd2() {
        // [    ] [    ] boulde switch 
        // player boulde switch light_off_AND
        // [    ] [    ] boulde switch
        // testLogicAnd1MMGK1658929484.005149

        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLogicAnd1MMGK1658929484.005149", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        
        // active 1 switch -> bulb is not activated.
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());

        // active 2 switch -> bulb is not activated.
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());
        
        // deactive 3 switch -> bulb is activated
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_on").size());
        assertEquals(0, getEntities(res, "light_bulb_off").size());
        // deactive a switch -> bulb is not activated

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());

    }

    @Test
    @DisplayName("Test activate a entity with logic AND, which is adjacent with four logical entities")
    public void testLogicAnd3() {
        // [    ] [    ] boulde switch [    ] 
        // player boulde switch lit_AND wire
        // [    ] [    ] boulde switch  wire
        // testLogicAnd3LORWI1658929666.0243626
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLogicAnd3LORWI1658929666.0243626", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        
        // active 1 switch -> bulb is not activated.
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());

        // active 2 switch -> bulb is not activated.
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());
        
        // deactive 3 switch -> bulb is activated
        res = dmc.tick(Direction.LEFT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_on").size());
        assertEquals(0, getEntities(res, "light_bulb_off").size());
        // deactive a switch -> bulb is not activated

        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_off").size());


    }

    @Test
    @DisplayName("Test activate a entity with logic OR, which is adjacent with two logical entities")
    public void testLogicOR() {
        // player boulde switch light_off_OR
        // [    ] [    ] boulde switch
        // testLogicOR5C81A1658929871.6619217
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLogicOR5C81A1658929871.6619217", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        
        // active 1 switch -> bulb is activated.
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, getEntities(res, "light_bulb_off").size());
        assertEquals(1, getEntities(res, "light_bulb_on").size());

        // active 2 switch -> bulb is activated.
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        // assertEquals(0, getEntities(res, "light_bulb_off").size());
        assertEquals(1, getEntities(res, "light_bulb_on").size());

        // deactive 1 switch -> bulb is activated
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_on").size());

        // deactive 1 switch -> bulb is not activated
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        assertEquals(0, getEntities(res, "light_bulb_on").size());


    }
    @Test
    @DisplayName("Test activate a entity with logic XOR, which is adjacent with two logical entities")
    public void testLogicXor() {
        // player boulde switch light_off_OR
        // [    ] [    ] boulde switch
        // testLogicXor3SIR31658930320.0123289
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLogicXor3SIR31658930320.0123289", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        
        // active 1 switch -> bulb is activated.
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, getEntities(res, "light_bulb_off").size());
        assertEquals(1, getEntities(res, "light_bulb_on").size());

        // active 2 switch -> bulb is not activated.
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        // assertEquals(0, getEntities(res, "light_bulb_off").size());
        assertEquals(0, getEntities(res, "light_bulb_on").size());

        // deactive 1 switch -> bulb is activated
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_on").size());

        // deactive 1 switch -> bulb is not activated
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.LEFT);
        assertEquals(0, getEntities(res, "light_bulb_on").size());
    }
    @Test
    @DisplayName("Test activate a entity with logic Co_AND, seprately two activate entities")
    public void testLogicCoAnd1() {
        // [    ] [    ] boulde switch  wire  
        // player boulde switch CO_AND  wire
        // [    ] [    ] boulde switch [    ]
        // testLogicCoAnd24SLJ81658930885.0734527
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLogicCoAnd24SLJ81658930885.0734527", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        
        // active 1 switch -> bulb is not activated.
        DungeonResponse res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, getEntities(res, "light_bulb_off").size());
        assertEquals(0, getEntities(res, "light_bulb_on").size());

        // active 2 switch -> bulb is not activated.
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.RIGHT);
        // assertEquals(0, getEntities(res, "light_bulb_off").size());
        assertEquals(0, getEntities(res, "light_bulb_on").size());
    } 
    @Test
    @DisplayName("Test activate a entity with logic Co_AND, activate two entities together. then save")
    public void testLogicCoAnd2() {
        // [    ] [    ] boulde switch  wire  
        // player boulde switch CO_AND  wire
        // [    ] [    ] boulde switch [    ]
        // testLogicCoAnd24SLJ81658930885.0734527
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initialResponse = dmc.newGame("testLogicCoAnd24SLJ81658930885.0734527", "c_Battletest_PlayerStrong");
        EntityResponse bulb = getEntities(initialResponse, "light_bulb_off").get(0);
        assertEquals("light_bulb_off", bulb.getType());
        
        DungeonResponse res = dmc.tick(Direction.UP);
        assertEquals(0, getEntities(res, "light_bulb_on").size());

        // active 1 switch and wires -> bulb is  activated.
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, getEntities(res, "light_bulb_on").size());
        
    } 
    
    @Test
    public void testActiveSwitchAndBombViaWire() {
    //     [    ] [    ] [    ] bomb      [    ] 
    //     player boulde switch switch_OR [    ] 
    //     [    ] [    ] [    ]  wire   bomb
    // testActiveSwitchAndBombViaWireZSSVG1659074852.9142034
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testActiveSwitchAndBombViaWireZSSVG1659074852.9142034", "c_Battletest_PlayerStrong");
        DungeonResponse res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.UP);
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);
        
        res = controller.tick(Direction.RIGHT);

        
    }

}
