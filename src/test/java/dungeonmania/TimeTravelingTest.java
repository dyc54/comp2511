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
import org.reflections.vfs.Vfs.Dir;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import dungeonmania.exceptions.InvalidActionException;

public class TimeTravelingTest {
    @Test
    public void testExpection() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravelFWQ7G1658488701.4102888", "c_Battletest_PlayerStrong");
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        assertThrows(IllegalArgumentException.class, () -> {
            controller.rewind(-1);
            controller.rewind(6);
        });
    }

    @Test
    public void testUseTimeTravel() {
        // player tm_trn [    ] [    ] [    ] [    ] [    ] sword  [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] mercen

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravelFWQ7G1658488701.4102888", "c_Battletest_PlayerStrong");
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        // rewind 5 ticks
        DungeonResponse Res = controller.rewind(5);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(0, 0), older_player.getPosition());
        assertTrue(getEntities(Res, "mercenary").size() == 1);
        EntityResponse mercenary = getEntities(Res, "mercenary").get(0);
        assertEquals(new Position(15, 15), mercenary.getPosition());
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        Res = controller.tick(Direction.RIGHT);
        // 
        assertTrue(getEntities(Res, "older_player").size() == 1);
        older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(5, 0), older_player.getPosition());
        // 
        Res = controller.tick(Direction.RIGHT);
        assertTrue(getEntities(Res, "older_player").size() == 0);

    }
    @Test
    public void testUseTimeTurnerBackOneTick() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravelFWQ7G1658488701.4102888", "c_Battletest_PlayerStrong");
        controller.tick(Direction.RIGHT);   // tick 1
        controller.tick(Direction.RIGHT);   // 2
        DungeonResponse Res = controller.rewind(1);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(1, 0), older_player.getPosition());
        Res = controller.tick(Direction.RIGHT);   // 3
        assertTrue(getEntities(Res, "older_player").size() == 1);
        older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(2, 0), older_player.getPosition());
        Res = controller.tick(Direction.RIGHT);   // 4
        assertTrue(getEntities(Res, "older_player").size() == 0);

    }
    @Test
    public void testUseTimeTravelEncounterEachOtherAndBattle() {
        // player tm_trn [    ] [    ] [    ] [    ] [    ]  sword [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] mercen

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravelFWQ7G1658488701.4102888", "c_Battletest_PlayerStrong");
        controller.tick(Direction.RIGHT);  // tick 1
        controller.tick(Direction.RIGHT);  // 2
        controller.tick(Direction.RIGHT);   // 3
        controller.tick(Direction.RIGHT);   // 4
        controller.tick(Direction.RIGHT);   // 5
        DungeonResponse Res = controller.rewind(5);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(0, 0), older_player.getPosition());
        assertTrue(getEntities(Res, "mercenary").size() == 1);
        EntityResponse mercenary = getEntities(Res, "mercenary").get(0);
        assertEquals(new Position(15, 15), mercenary.getPosition());
        Res = controller.tick(Direction.LEFT);
        Res = controller.tick(Direction.LEFT);
        assertTrue(Res.getBattles().size() == 0);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(2, 0), older_player.getPosition());
        Res = controller.tick(Direction.LEFT);
        assertTrue(Res.getBattles().size() == 1);
        // assertTrue(getEntities(Res, "older_player").size() == 1);
        // older_player = getEntities(Res, "older_player").get(0);
        // assertEquals(new Position(2, 0), older_player.getPosition());

    }
    @Test
    public void testUseTimeTravelEncounterEachOtherNoBattle() {
        // [___1] time_t [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] mercen
        // 1: player, sun_stone

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("testUseTimeTravelEncounterEachOtherNoBattleIV0HA1659104817.6438503", "c_Battletest_PlayerStrong");
        controller.tick(Direction.RIGHT);  // tick 1
        controller.tick(Direction.RIGHT);  // 2
        controller.tick(Direction.RIGHT);   // 3
        controller.tick(Direction.RIGHT);   // 4
        controller.tick(Direction.RIGHT);   // 5
        DungeonResponse Res = controller.rewind(5);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(0, 0), older_player.getPosition());
        assertTrue(getEntities(Res, "mercenary").size() == 1);
        EntityResponse mercenary = getEntities(Res, "mercenary").get(0);
        assertEquals(new Position(15, 15), mercenary.getPosition());
        Res = controller.tick(Direction.LEFT);
        Res = controller.tick(Direction.LEFT);
        assertTrue(Res.getBattles().size() == 0);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(2, 0), older_player.getPosition());
        Res = controller.tick(Direction.LEFT);
        assertTrue(Res.getBattles().size() == 0);

    }
    @Test
    public void testUseTimeTravelInventory() {
        // player tm_trn [    ] [    ] [    ] [    ] [    ]  sword [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
        // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] mercen

        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravelFWQ7G1658488701.4102888", "c_Battletest_PlayerStrong");
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        DungeonResponse Res = controller.rewind(5);
        assertTrue(getEntities(Res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(2, 0), older_player.getPosition());
        assertTrue(getInventory(Res, "sword").size() == 1);
        

    }
    @Test
    public void testTimeTravelViaProtalWithin30Tick() {
     // [___1] tm_tPl [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] spider
    // 1: player, sword
    // timeTravel21SIY1658490236.018598
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravel21SIY1658490236.018598", "c_Battletest_PlayerStrong");
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.UP);
        controller.tick(Direction.RIGHT);
        DungeonResponse res = controller.tick(Direction.DOWN);
        assertTrue(getEntities(res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(0, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);
        assertTrue(getEntities(res, "older_player").size() == 1);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-1, 0), older_player.getPosition());
    }
    @Test
    public void testOlderPlayerWalkIntoPatroal() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravel21SIY1658490236.018598", "c_Battletest_PlayerStrong");
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.UP);
        controller.tick(Direction.RIGHT);
        DungeonResponse res = controller.tick(Direction.DOWN);
        assertTrue(getEntities(res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(0, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-1, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-2, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-1, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(0, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(0, -1), older_player.getPosition());
        res = controller.tick(Direction.UP);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(1, -1), older_player.getPosition());
        res = controller.tick(Direction.UP);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(1, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);
        assertTrue(getEntities(res, "older_player").size() == 1);
    }
    // [___1] tm_tPl [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ]
    // [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] spider
    // 1: player, sword
    // timeTravel21SIY1658490236.018598
    @Test
    public void testTimeTravelViaProtal() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravel21SIY1658490236.018598", "c_Battletest_PlayerStrong");
        controller.tick(Direction.LEFT);   // tick 1
        controller.tick(Direction.LEFT);   // tick 2
        controller.tick(Direction.LEFT);   // tick 3
        controller.tick(Direction.LEFT);   // tick 4
        controller.tick(Direction.LEFT);   // tick 5
        controller.tick(Direction.LEFT);   // tick 6
        controller.tick(Direction.LEFT);   // tick 7

        controller.tick(Direction.DOWN);   // tick 8
        controller.tick(Direction.DOWN);   // tick 9
        controller.tick(Direction.DOWN);   // tick 10
        controller.tick(Direction.DOWN);   // tick 11
        controller.tick(Direction.DOWN);   // tick 12
        controller.tick(Direction.DOWN);   // tick 13
        controller.tick(Direction.DOWN);   // tick 14
        
        controller.tick(Direction.RIGHT);   // tick 15
        controller.tick(Direction.RIGHT);   // tick 16
        controller.tick(Direction.RIGHT);   // tick 17
        controller.tick(Direction.RIGHT);   // tick 18
        controller.tick(Direction.RIGHT);   // tick 19
        controller.tick(Direction.RIGHT);   // tick 20
        controller.tick(Direction.RIGHT);   // tick 21
        controller.tick(Direction.RIGHT);   // tick 22
        controller.tick(Direction.RIGHT);   // tick 23

        controller.tick(Direction.UP);   // tick 24
        controller.tick(Direction.UP);   // tick 25
        controller.tick(Direction.UP);   // tick 26
        controller.tick(Direction.UP);   // tick 27
        controller.tick(Direction.UP);   // tick 28
        controller.tick(Direction.UP);   // tick 29
        controller.tick(Direction.UP);   // tick 30

        DungeonResponse res = controller.tick(Direction.LEFT); // tick 31
        // time travel  tick 31 -> end of tick 1
        assertTrue(getEntities(res, "player").size() == 1);
        EntityResponse player = getEntities(res, "player").get(0);
        assertEquals(new Position(1, 0), player.getPosition());

        assertTrue(getEntities(res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-1, 0), older_player.getPosition());
        EntityResponse spider = getEntities(res, "spider").get(0);
        assertEquals(new Position(10, 9), spider.getPosition());
        res = controller.tick(Direction.UP);

        assertTrue(getEntities(res, "older_player").size() == 1);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-2, 0), older_player.getPosition());


    }

}
