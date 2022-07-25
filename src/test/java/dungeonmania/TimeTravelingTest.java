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
import org.reflections.vfs.Vfs.Dir;

import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class TimeTravelingTest {
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
        assertTrue(getEntities(Res, "older_player").size() == 1);
        older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(5, 0), older_player.getPosition());
        // 
        Res = controller.tick(Direction.RIGHT);
        assertTrue(getEntities(Res, "older_player").size() == 0);

    }
    @Test
    public void testUseTimeTravelEncounterEachOther() {
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
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);
        controller.tick(Direction.LEFT);

        controller.tick(Direction.DOWN);
        controller.tick(Direction.DOWN);
        controller.tick(Direction.DOWN);
        controller.tick(Direction.DOWN);
        controller.tick(Direction.DOWN);
        controller.tick(Direction.DOWN);
        controller.tick(Direction.DOWN);
        
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);
        controller.tick(Direction.RIGHT);

        controller.tick(Direction.UP);
        controller.tick(Direction.UP);
        controller.tick(Direction.UP);
        controller.tick(Direction.UP);
        controller.tick(Direction.UP);
        controller.tick(Direction.UP);
        controller.tick(Direction.UP);

        DungeonResponse res = controller.tick(Direction.LEFT);
        assertTrue(getEntities(res, "player").size() == 1);
        EntityResponse player = getEntities(res, "player").get(0);
        assertEquals(new Position(2, 0), player.getPosition());

        assertTrue(getEntities(res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-2, 0), older_player.getPosition());
        res = controller.tick(Direction.UP);

        assertTrue(getEntities(res, "older_player").size() == 1);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(-3, 0), older_player.getPosition());


    }
    // @Test
    public void testTimeTravelTwice() {
        DungeonManiaController controller = new DungeonManiaController();
        controller.newGame("timeTravel21SIY1658490236.018598", "c_Battletest_PlayerStrong");
        for (int i = 0; i < 16; i++) {
            controller.tick(Direction.LEFT);
        }
        for (int i = 0; i < 16; i++) {
            controller.tick(Direction.RIGHT);
        }
        controller.tick(Direction.RIGHT);
        for (int i = 0; i < 16; i++) {
            controller.tick(Direction.DOWN);
        }
        for (int i = 0; i < 16; i++) {
            controller.tick(Direction.UP);
        }
        DungeonResponse res = controller.tick(Direction.RIGHT);
        assertTrue(getEntities(res, "older_player").size() == 1);
        EntityResponse older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(0, 3), older_player.getPosition());

        res = controller.tick(Direction.UP);
        assertTrue(getEntities(res, "older_player").size() == 1);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(0, 4), older_player.getPosition());

        res = controller.tick(Direction.UP);
        assertTrue(getEntities(res, "older_player").size() == 1);
        older_player = getEntities(res, "older_player").get(0);
        assertEquals(new Position(0, 5), older_player.getPosition());

    }
}
