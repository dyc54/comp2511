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
        // player tm_trn [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
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
        // player tm_trn [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
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
        assertTrue(getEntities(Res, "older_player").size() == 1);
        older_player = getEntities(Res, "older_player").get(0);
        assertEquals(new Position(2, 0), older_player.getPosition());
        Res = controller.tick(Direction.LEFT);
        assertTrue(getEntities(Res, "older_player").size() == 1);

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
}
