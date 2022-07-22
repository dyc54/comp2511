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
        // timeTravelFWQ7G1658488701.4102888
        assert(false);
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
