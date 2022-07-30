package dungeonmania;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static dungeonmania.TestUtils.getEntities;
import static dungeonmania.TestUtils.getInventory;
import static dungeonmania.TestUtils.getValueFromConfigFile;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import dungeonmania.buildableEntities.MidnightArmour;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
public class BattleTest {
        // private static DungeonResponse genericMercenarySequence(DungeonManiaController controller, String configFile) {
        //     /*
        //     * exit wall wall wall
        //     * player [ ] merc wall
        //     * wall wall wall wall
        //     */
        //     DungeonResponse initialResponse = controller.newGame("d_battleTest_basicMercenary", configFile);
        //     int mercenaryCount = countEntityOfType(initialResponse, "mercenary");

        //     assertEquals(1, countEntityOfType(initialResponse, "player"));
        //     assertEquals(1, mercenaryCount);
        //     return controller.tick(Direction.RIGHT);
        // }

    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies,
                String configFilePath) {
            List<RoundResponse> rounds = battle.getRounds();
            double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
            double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
            double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
            double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
            // System.out.println(playerAttack);
        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -(enemyAttack / 10));
            assertEquals(round.getDeltaEnemyHealth(), -(playerAttack / 5));
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
            // System.out.println(String.format("%f %f", enemyHealth, playerHealth));
        }
        // System.out.println(enemyHealth);

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }
    private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies,
                String configFilePath,boolean sword, boolean shield, boolean bow, int expRound) {
            List<RoundResponse> rounds = battle.getRounds();
            double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
            double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
            double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
            double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
            double swordbonus = sword ? Double.parseDouble(getValueFromConfigFile("sword_attack", configFilePath)) : 0;
            double bowbonus = bow ? 2 : 1;
            double shieldbonus = shield ? Double.parseDouble(getValueFromConfigFile("shield_defence", configFilePath)): 0;
            // System.out.println(playerAttack);
        int i = 0;
        for (RoundResponse round : rounds) {
            if (i == expRound) {
                break;
            }
            assertEquals(round.getDeltaCharacterHealth(), -(((enemyAttack - shieldbonus) < 0 ?  0 : enemyAttack - shieldbonus)/ 10));
            assertEquals(round.getDeltaEnemyHealth(), -(bowbonus * (playerAttack + swordbonus) / 5));
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
            i++;
            // System.out.println(String.format("%f %f", enemyHealth, playerHealth));
        }
        // System.out.println(enemyHealth);
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
            // System.out.println(playerAttack);
        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -(((enemyAttack - shieldbonus) < 0 ?  0 : enemyAttack - shieldbonus)/ 10));
            assertEquals(round.getDeltaEnemyHealth(), -(bowbonus * (playerAttack + swordbonus) / 5));
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
            // System.out.println(String.format("%f %f", enemyHealth, playerHealth));
        }
        // System.out.println(enemyHealth);

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }

        private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies,
            String configFilePath,boolean sword, boolean shield, boolean bow, boolean MidnightArmour) {
            List<RoundResponse> rounds = battle.getRounds();
            double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
            double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_health", configFilePath));
            double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
            double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
            double swordbonus = sword ? Double.parseDouble(getValueFromConfigFile("sword_attack", configFilePath)) : 0;
            double bowbonus = bow ? 2 : 1;
            double MidnightArmourAttackBonus = MidnightArmour ? Double.parseDouble(getValueFromConfigFile("midnight_armour_attack", configFilePath)) : 0;
            double MidnightArmourDefenceBonus = MidnightArmour ? Double.parseDouble(getValueFromConfigFile("midnight_armour_defence", configFilePath)) : 0;
            double shieldbonus = shield ? Double.parseDouble(getValueFromConfigFile("shield_defence", configFilePath)): 0;
            // System.out.println(playerAttack);
        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), -(((enemyAttack - shieldbonus - MidnightArmourDefenceBonus) < 0 ?  0 : enemyAttack - shieldbonus - MidnightArmourDefenceBonus)/ 10));
            assertEquals(round.getDeltaEnemyHealth(), -(bowbonus * (playerAttack + swordbonus + MidnightArmourAttackBonus) / 5));
            enemyHealth += round.getDeltaEnemyHealth();
            playerHealth += round.getDeltaCharacterHealth();
            // System.out.println(String.format("%f %f", enemyHealth, playerHealth));
        }
        // System.out.println(enemyHealth);

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }
    @Test
    @DisplayName("Test battles three enemies consecutively and defeats them")
    public void testBattleConsecutively() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("battleConsecutivelyMMDXC1659143561.666144", "c_Battletest_PlayerStrong");
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        assertEquals(3, postBattleResponse.getBattles().size());
        assertEquals(0, getEntities(postBattleResponse, "player").size());
    }

    @Test
    @DisplayName("Test the player can win battle with spider")
    public void testBattleWithSpiderWin() {
        /**
         * player [    ] 
         * [    ] spider 
         */
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("battleWithSpiderWinVKQT81657768515.1290405", "c_Battletest_PlayerStrong");
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        controller.tick(Direction.RIGHT);
        assertBattleCalculations("spider", battle, true, "c_Battletest_PlayerStrong");
    }
    @Test
    @DisplayName("Test the player can lose battle with spider")
    public void testBattleWithSpiderLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("battleWithSpiderWinVKQT81657768515.1290405", "c_BattleTest_playerweak");
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        controller.tick(Direction.RIGHT);
        assertBattleCalculations("spider",battle, false, "c_BattleTest_playerweak");
    }
    @Test
    @DisplayName("Test the player can lose battle with zombie")
    public void testBattleWithZombieWin() {
        DungeonManiaController controller = new DungeonManiaController();
        //  player [    ] [    ] 
        //  wall  zombie  wall
        //  [    ]  wall  [    ]
        
        DungeonResponse initialResponse = controller.newGame("BattleWithZombieM6DU51657772152.1681082", "c_Battletest_PlayerStrong");
        DungeonResponse postBattleResponse = controller.tick(Direction.DOWN);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        controller.tick(Direction.RIGHT);
        assertBattleCalculations("zombie",battle, true, "c_Battletest_PlayerStrong");
        // DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        //         "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithZombieLost() {
        DungeonManiaController controller = new DungeonManiaController();
        //  player  wall  [    ] 
        //  [    ]  zombie  wall
        //  [    ]  wall  [    ]
        DungeonResponse initialResponse = controller.newGame("BattleWithZombieM6DU51657772152.1681082", "c_BattleTest_playerweak");
        DungeonResponse postBattleResponse = controller.tick(Direction.DOWN);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        controller.tick(Direction.RIGHT);
        assertBattleCalculations("zombie",battle, false, "c_BattleTest_playerweak");
    }

    @Test
    public void testBattleWithEnemyInvincibilityPotionWin() {
        assertDoesNotThrow( () -> {
            DungeonManiaController controller = new DungeonManiaController();
            DungeonResponse initialResponse = controller.newGame("testinvincibility_potionWithSpider4LQ3G1657779449.1987035", "c_BattleTest_playerweak");
            String invincibility_potion_id = getInventory(initialResponse, "invincibility_potion").get(0).getId();
            DungeonResponse postBattleResponse = controller.tick(invincibility_potion_id);
            assertTrue(getEntities(postBattleResponse, "spider").size() == 1);
            assertTrue(postBattleResponse.getBattles().size() == 1);
            assertEquals(getEntities(postBattleResponse, "spider").get(0).getPosition(), new Position(0,0));
        });
        // DungeonResponse ; 
        // });
        // assertBattleCalculations("spider", battle, true, "c_BattleTest_playerweak");
    }
    
    @Test
    public void testBattleWithEnemyInvincibilityPotionWithZombie() {
        assertDoesNotThrow( () -> {
            DungeonManiaController controller = new DungeonManiaController();
            DungeonResponse initialResponse = controller.newGame("d_potionWithZombie", "c_BattleTest_playerweak");
            String invincibility_potion_id = getInventory(initialResponse, "invincibility_potion").get(0).getId();
            DungeonResponse postBattleResponse = controller.tick(invincibility_potion_id);
            assertTrue(getEntities(postBattleResponse, "zombie").size() == 1);
            assertTrue(postBattleResponse.getBattles().size() == 1);
            assertNotEquals(getEntities(postBattleResponse, "zombie").get(0).getPosition(), new Position(2,3));
        });
    }


    @Test
    public void testBattleWithEnemyInvincibilityPotionLost() {
        // DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        //         "c_battleTests_basicMercenaryPlayerDies");
        assertDoesNotThrow( () -> {
            DungeonManiaController controller = new DungeonManiaController();
            DungeonResponse initialResponse = controller.newGame("d_potionWithZombie", "c_BattleTest_StrongEnemies");
            String invincibility_potion_id = getInventory(initialResponse, "invincibility_potion").get(0).getId();
            DungeonResponse postBattleResponse = controller.tick(invincibility_potion_id);
            assertTrue(postBattleResponse.getBattles().size() == 1);
            assertTrue(getEntities(postBattleResponse, "zombie").size() == 1);
            assertTrue(getEntities(postBattleResponse, "player").size() == 0);
            assertNotEquals(getEntities(postBattleResponse, "zombie").get(0).getPosition(), new Position(2,3));

        });

    }
    @Test
    public void testBattleWithEnemyInvisibilityPotionWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testInvisibilityPotionCGCJB1657792158.6375983", "c_Battletest_PlayerStrong");
        String potion_id = getInventory(initialResponse, "invisibility_potion").get(0).getId();
        assertDoesNotThrow( () -> {
            controller.tick(potion_id);
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);// DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        assertTrue(postBattleResponse.getBattles().size() == 0);
        assertTrue(getEntities(postBattleResponse, "spider").size() == 1);
        //         "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testBattleSwordDOG901657792864.4144611", "c_BattleTest_playerweak");
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertTrue(getInventory(initialResponse, "sword").size() == 1);
        assertBattleCalculations("spider", battle, true, "c_BattleTest_playerweak", true, false, false);
        DungeonResponse next = controller.tick(Direction.RIGHT);
        assertTrue(getInventory(next, "sword").size() == 0);
    }
    @Test
    public void testBattleWithEnemyBow() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testBattlewithBowYEKBM1657806630.6273608", "c_BattleTest_playerweak");
        assertDoesNotThrow( () -> {
            controller.build("bow");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, true, "c_BattleTest_playerweak", false, false, true);
        
        // DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        //         "c_battleTests_basicMercenaryPlayerDies");
    }

    @Test
    public void testBattleWithEnemyMidnightArmour() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battletest_nightarmour", "c_BattleTest_playerweak");
        assertDoesNotThrow( () -> {
            controller.build("midnight_armour");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("mercenary", battle, true, "c_BattleTest_playerweak", false, false, false, true);
        
    }

    @Test
    public void testBattleWithEnemyMidnightArmourStrongPlayer() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_battletest_nightarmour", "c_Battletest_PlayerStrong");
        assertDoesNotThrow( () -> {
            controller.build("midnight_armour");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("mercenary", battle, true, "c_Battletest_PlayerStrong", false, false, false, true); 
    }

    @Test
    public void testBattleWithEnemyMidnightArmourAndSword() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_Battletest_midnightArmourAndSword", "c_Battletest_PlayerStrong");
        assertDoesNotThrow( () -> {
            controller.build("midnight_armour");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("mercenary", battle, true, "c_Battletest_PlayerStrong", true, false, false, true); 
    }

    @Test
    public void testBattleWithEnemyMidnightArmourAndBow() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_Battletest_midnightArmourAndBow", "c_Battletest_PlayerStrong");
        assertDoesNotThrow( () -> {
            controller.build("midnight_armour");
        });
        assertDoesNotThrow( () -> {
            controller.build("bow");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("mercenary", battle, true, "c_Battletest_PlayerStrong", false, false, true, true); 
    }

    @Test
    public void testBattleWithEnemyMidnightArmourAndShield() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("d_Battletest_midnightArmourAndShield", "c_Battletest_PlayerStrong");
        assertDoesNotThrow( () -> {
            controller.build("midnight_armour");
        });
        assertDoesNotThrow( () -> {
            controller.build("shield");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("mercenary", battle, true, "c_Battletest_PlayerStrong", false, true, false, true); 
    }


    @Test
    public void testBattleWithEnemyShieldWin() {
        // DungeonManiaController controller = new DungeonManiaController();
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testBattleShield5YIPL1657809090.6974766", "c_BattleTest_playerweak");
        assertDoesNotThrow( () -> {
            controller.build("shield");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, true, "c_BattleTest_playerweak", false, true, false);

        // DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        //         "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyShieldGtEnemyAttack() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testBattleShield5YIPL1657809090.6974766", "c_Battletest_PlayerStrong");
        assertDoesNotThrow( () -> {
            controller.build("shield");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, true, "c_Battletest_PlayerStrong", false, true, false);
    }
    @Test
    public void testBattleWithEnemySwordAndBowWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testBattleBuildAllV4EJM1657810665.3420236", "c_Battletest_PlayerStrong");
        assertDoesNotThrow( () -> {
            controller.build("bow");
            // controller.build("sword");
        });
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, true, "c_Battletest_PlayerStrong", true, false, true);
        // DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        //         "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordAndBowAndShieldWin() {
        // DungeonResponse postBattleResponse = genericMercenarySequence(controller,
        //         "c_battleTests_basicMercenaryPlayerDies");
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testBattleBuildAllV4EJM1657810665.3420236", "c_Battletest_PlayerStrong");
        assertDoesNotThrow( () -> {
            controller.build("bow");
            controller.build("shield");
        });
        // List<ItemResponse> inv = getInventory(next, "bow");
        // assertEquals(0, inv.size());
        DungeonResponse postBattleResponse = controller.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        assertBattleCalculations("spider", battle, true, "c_Battletest_PlayerStrong", true, true, true);
        DungeonResponse next = controller.tick(Direction.RIGHT);
        List<ItemResponse> inv = getInventory(next, "bow");
        assertEquals(0, inv.size());

    }
    @Test 
    @DisplayName("Test buildables durability")
    public void testBuildablesDurability() {
    //     [    ]  wall  [    ] 
    //     wall  [___ 1]  door
    //    [    ]  wall  [___ 2]
    //    1: player, sword, wood, wood, wood, key, arrow, arrow, arrow, sun stone, invincibility_potion, invisibility_potion, invisibility_potion, invincibility_potion
    //    2: mercenary, mercenary, mercenary, mercenary
    // testDurabilityED1KO1659145404.9214625
    DungeonManiaController controller = new DungeonManiaController();
    DungeonResponse initialResponse = controller.newGame("testDurabilityED1KO1659145404.9214625", "testDurability");
    assertDoesNotThrow( () -> {
        controller.build("bow");
        controller.build("shield");
    });
    DungeonResponse next = controller.tick(Direction.RIGHT);
    // BattleResponse battle = next.getBattles().get(0);
    assertBattleCalculations("mercenary", next.getBattles().get(0), true, "testDurability", true, true, true);
    assertBattleCalculations("mercenary", next.getBattles().get(1), true, "testDurability", true, true, false);
    assertBattleCalculations("mercenary", next.getBattles().get(2), true, "testDurability", true, false, false);
    assertBattleCalculations("mercenary", next.getBattles().get(3), true, "testDurability", false, false, false);

    }
    @Test 
    @DisplayName("Test potions durability")
    public void testPotionsDurability() {
    //     [    ]  wall  [    ] 
    //     wall  [___ 1]  door
    //    [    ]  wall  [___ 2]
    //    1: player, sword, wood, wood, wood, key, arrow, arrow, arrow, sun stone, invincibility_potion, invisibility_potion, invisibility_potion, invincibility_potion
    //    2: mercenary, mercenary, mercenary, mercenary
    // testDurabilityED1KO1659145404.9214625
    DungeonManiaController controller = new DungeonManiaController();
    DungeonResponse initialResponse = controller.newGame("testDurabilityED1KO1659145404.9214625", "testDurability");
    assertDoesNotThrow( () -> {
        controller.tick(getInventory(initialResponse, "invincibility_potion").get(0).getId());
    });
    DungeonResponse next = controller.tick(Direction.RIGHT);
    // BattleResponse battle = next.getBattles().get(0);
    assertBattleCalculations("mercenary", next.getBattles().get(0), false, "testDurability", true, false, false, 1);
    assertBattleCalculations("mercenary", next.getBattles().get(1), false, "testDurability", true, false, false, 1);
    assertBattleCalculations("mercenary", next.getBattles().get(2), false, "testDurability", true, false, false, 1);
    assertBattleCalculations("mercenary", next.getBattles().get(3), false, "testDurability", false, false, false, 1);

    next = controller.tick(Direction.LEFT);
    assertBattleCalculations("mercenary", next.getBattles().get(0 + 4), false, "testDurability", false, false, false, 10000000);
    assertBattleCalculations("mercenary", next.getBattles().get(1 + 4), false, "testDurability", false, false, false, 10000000);
    assertBattleCalculations("mercenary", next.getBattles().get(2 + 4), false, "testDurability", false, false, false, 10000000);
    assertBattleCalculations("mercenary", next.getBattles().get(3 + 4), false, "testDurability", false, false, false, 10000000);

    }
    @Test 
    @DisplayName("Test potions durability")
    public void testPotionsDurabilityQueued() {
        // [    ]  wall   wall  [    ] [    ] 
        //  wall  [___ 1]  door  wall
        // [    ]  wall  [___ 2] wall
        // [    ] [    ]  wall  [    ] [    ]
        // 1: player, sword, wood, wood, wood, sunstone, arrow, arrow, arrow, key, invincibility_potion, invisibility_potion, invisibility_potion, invincibility_potion
        // 2: mercenary, mercenary, mercenary
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse initialResponse = controller.newGame("testDurabilityED1KO1659145404.9214625", "TestPotionQueue");
        assertDoesNotThrow( () -> {
            controller.tick(getInventory(initialResponse, "invincibility_potion").get(0).getId());  // tick 0
            controller.tick(getInventory(initialResponse, "invisibility_potion").get(0).getId());   // tick 1
        });
        // Effect was queue
        DungeonResponse next = controller.tick(Direction.RIGHT); // tick 2 invincibility_potion effect end at the end of tick 2 
        assertEquals(4, next.getBattles().size());

        assertBattleCalculations("mercenary", next.getBattles().get(0), false, "TestPotionQueue", true, false, false, 1);
        assertBattleCalculations("mercenary", next.getBattles().get(1), false, "TestPotionQueue", true, false, false, 1);
        assertBattleCalculations("mercenary", next.getBattles().get(2), false, "TestPotionQueue", true, false, false, 1);
        assertBattleCalculations("mercenary", next.getBattles().get(3), false, "TestPotionQueue", false, false, false, 1);

        next = controller.tick(Direction.LEFT);
        assertEquals(4, next.getBattles().size());
        next = controller.tick(Direction.RIGHT);
        assertEquals(4, next.getBattles().size());
        next = controller.tick(Direction.RIGHT);
        assertEquals(4, next.getBattles().size());
        next = controller.tick(Direction.DOWN);
        assertEquals(4, next.getBattles().size());
        next = controller.tick(Direction.UP);
        assertEquals(4, next.getBattles().size());
    }

}
