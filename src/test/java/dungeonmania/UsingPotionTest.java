package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class UsingPotionTest {

    public DungeonResponse playerMoveController(DungeonManiaController dmc, Direction direction, int movetime){
        DungeonResponse res = dmc.tick(direction);
        for (int i = 1; i < movetime; i++) {
            res = dmc.tick(direction);
        }
        return res;
    }

    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies,
    String configFilePath) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
        for (RoundResponse round : rounds) {
        assertEquals(round.getDeltaCharacterHealth(), -(enemyAttack / 10));
        assertEquals(round.getDeltaEnemyHealth(), -(playerAttack / 5));
        enemyHealth += round.getDeltaEnemyHealth();
        playerHealth += round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
        assertTrue(enemyHealth <= 0);
        } else {
        assertTrue(playerHealth <= 0);
        }
    }
private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies,
        String configFilePath,boolean sword, boolean shield, boolean bow) {
        List<RoundResponse> rounds = battle.getRounds();
        double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
        double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
        double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
        double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
        double swordbonus = sword ? Double.parseDouble(getValueFromConfigFile("sword_attack", configFilePath)) : 0;
        double bowbonus = bow ? 2 : 1;
        double shieldbonus = shield ? Double.parseDouble(getValueFromConfigFile("shield_defence", configFilePath)): 0;
        for (RoundResponse round : rounds) {
        assertEquals(round.getDeltaCharacterHealth(), -(((enemyAttack - shieldbonus) < 0 ?  0 : enemyAttack - shieldbonus)/ 10));
        assertEquals(round.getDeltaEnemyHealth(), -(bowbonus * (playerAttack + swordbonus) / 5));
        enemyHealth += round.getDeltaEnemyHealth();
        playerHealth += round.getDeltaCharacterHealth();
        }
        if (enemyDies) {
        assertTrue(enemyHealth <= 0);
        } else {
        assertTrue(playerHealth <= 0);
        }
    }
    
    @Test
    @DisplayName("Test the player inventory")
    public void testPickUpPotion() {

        DungeonManiaController dmc = new DungeonManiaController();

        DungeonResponse res = dmc.newGame("d_potionDurablePlayerStatu",
                "c_collectTests");

        // pick up potion
        res = playerMoveController(dmc, Direction.RIGHT, 6);
        assertEquals(3, getInventory(res, "invisibility_potion").size());
        assertEquals(3, getInventory(res, "invincibility_potion").size());
    }

    @Test
    @DisplayName("Test the player use invincibility_potion")
    public void testPlayerUsingInvincibilityPotion() throws IllegalArgumentException, InvalidActionException {
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();

            DungeonResponse res = dmc.newGame("d_potionDurablePlayerStatu",
                    "c_collectTests");
    
            // pick up potion
            res = playerMoveController(dmc, Direction.RIGHT, 6);
            assertEquals(3, getInventory(res, "invisibility_potion").size());
            assertEquals(3, getInventory(res, "invincibility_potion").size());
    
            //get potion id
            String invisibility_potion_1_ID = getInventory(res, "invisibility_potion").get(0).getId();
            res = dmc.tick(invisibility_potion_1_ID);
            assertEquals(2, getInventory(res, "invisibility_potion").size());
            assertEquals(3, getInventory(res, "invincibility_potion").size());
            
        });
    }

    @Test
    @DisplayName("Test the player use Use ItemFail IllegalArgumentException")
    public void testUseItemFail() throws IllegalArgumentException, InvalidActionException {
    
        assertThrows(IllegalArgumentException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame("d_potionDurablePlayerStatu", "c_Battletest_PlayerStrong");
            res = playerMoveController(dmc, Direction.RIGHT, 7);

            String ID = getInventory(res, "wood").get(0).getId();
            res = dmc.tick(ID);
            
        });
    }

    @Test
    @DisplayName("Test the player use Use ItemFail InvalidActionException")
    public void testUseItemFail2() throws IllegalArgumentException, InvalidActionException {
        assertThrows(InvalidActionException.class, () -> {
            DungeonManiaController dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame("d_potionDurablePlayerStatu", "c_Battletest_PlayerStrong");
            res = playerMoveController(dmc, Direction.RIGHT, 7);

            String ID = getInventory(res, "wood").get(0).getId();
            res = dmc.tick(ID+"1");
            
        });
    }


    @Test
    @DisplayName("Test the player use can invisibility_potion no battle with mercenary")
    public void testBattleInvisibilityPotion() throws IllegalArgumentException, InvalidActionException {
        /**
         * [    ] [    ] [    ][    ][    ][    ][    ]   [    ]
         * [    ] player                        mercenary [    ]
         * [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
         */
        assertDoesNotThrow(() -> {
            DungeonManiaController dmc = new DungeonManiaController();
            DungeonResponse res = dmc.newGame("d_potionBattleTest", "c_Battletest_PlayerStrong");
            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(2,1), getEntities(res, "player").get(0).getPosition());
            assertEquals(new Position(5,1), getEntities(res, "mercenary").get(0).getPosition());

            res = dmc.tick(Direction.RIGHT);
            assertEquals(new Position(3,1), getEntities(res, "player").get(0).getPosition());
            assertEquals(new Position(4,1), getEntities(res, "mercenary").get(0).getPosition());

            //player takes invisibility_potion, mercenary loses player coordinates
            String invisibility_potion_1_ID = getInventory(res, "invisibility_potion").get(0).getId();
            res = dmc.tick(invisibility_potion_1_ID);
            assertEquals(new Position(3,1), getEntities(res, "player").get(0).getPosition());
            assertEquals(0, res.getBattles().size());
            
        });
        
    }


    @Test
    @DisplayName("Test the player use invincibility_potion battle with mercenary")
    public void testBattleInvincibilityPotion() throws IllegalArgumentException, InvalidActionException {
        /**
         * [    ] [    ] [    ][    ][    ][    ][    ]   [    ]
         * [    ] player                        mercenary [    ]
         * [    ] [    ] [    ] [    ] [    ] [    ] [    ] [    ] 
         */
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_potionBattleTest2", "c_Battletest_PlayerStrong");
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(2,1), getEntities(res, "player").get(0).getPosition());
        assertEquals(new Position(5,1), getEntities(res, "mercenary").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(3,1), getEntities(res, "player").get(0).getPosition());
        assertEquals(new Position(4,1), getEntities(res, "mercenary").get(0).getPosition());

        //player takes invincibility_potion，have one battle,then mercenary run
        String invincibility_potion_1_ID = getInventory(res, "invincibility_potion").get(0).getId();
        res = dmc.tick(invincibility_potion_1_ID);
        assertEquals(new Position(3,1), getEntities(res, "player").get(0).getPosition());
        assertEquals(1, res.getBattles().size());
        assertNotEquals(new Position(3,1), getEntities(res, "mercenary").get(0).getPosition());
        
        //invisibility_potion invalid, mercenary go back to find player
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4,1), getEntities(res, "player").get(0).getPosition());
        assertEquals(new Position(3,1), getEntities(res, "mercenary").get(0).getPosition());

    }
    @Test
    public void testPotionwithAlly() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("testAllyCEYHC1657994389.453653", "c_Battletest_PlayerStrong");
        String id = getEntities(res, "mercenary").get(0).getId();
        assertDoesNotThrow(()->{
            dmc.interact(id);
            String nid = getInventory(res, "invisibility_potion").get(0).getId();
            dmc.tick(nid);
            dmc.tick(Direction.LEFT);
            dmc.tick(Direction.LEFT);
            dmc.tick(Direction.LEFT);
            dmc.tick(Direction.LEFT);
            dmc.tick(Direction.LEFT);
            dmc.tick(Direction.LEFT);
            dmc.tick(Direction.LEFT);
        });

    }
    @Test
    public void testPotionwithAlly2() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("testAllyBKZQI1657998807.906683", "c_Battletest_PlayerStrong");
        String id = getEntities(res, "mercenary").get(0).getId();
        assertDoesNotThrow(()->{
            dmc.interact(id);
            // String nid = getInventory(res, "invisibility_potion").get(0).getId();
            dmc.tick(Direction.DOWN);
            dmc.tick(Direction.DOWN);
            dmc.tick(Direction.DOWN);
            dmc.tick(Direction.DOWN);
            dmc.tick(Direction.DOWN);
            dmc.tick(Direction.UP);
            dmc.tick(Direction.UP);
            dmc.tick(Direction.UP);
            dmc.tick(Direction.UP);
            dmc.tick(Direction.UP);
            dmc.tick(Direction.UP);
        });

    }

}
