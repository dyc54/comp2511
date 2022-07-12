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
public class BattleTest {
        private static DungeonResponse genericMercenarySequence(DungeonManiaController controller, String configFile) {
            /*
            * exit wall wall wall
            * player [ ] merc wall
            * wall wall wall wall
            */
            DungeonResponse initialResponse = controller.newGame("d_battleTest_basicMercenary", configFile);
            int mercenaryCount = countEntityOfType(initialResponse, "mercenary");

            assertEquals(1, countEntityOfType(initialResponse, "player"));
            assertEquals(1, mercenaryCount);
            return controller.tick(Direction.RIGHT);
        }

        private void assertBattleCalculations(String enemyType, BattleResponse battle, boolean enemyDies,
                String configFilePath) {
            List<RoundResponse> rounds = battle.getRounds();
            double playerHealth = Double.parseDouble(getValueFromConfigFile("player_health", configFilePath));
            double enemyHealth = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));
            double playerAttack = Double.parseDouble(getValueFromConfigFile("player_attack", configFilePath));
            double enemyAttack = Double.parseDouble(getValueFromConfigFile(enemyType + "_attack", configFilePath));

        for (RoundResponse round : rounds) {
            assertEquals(round.getDeltaCharacterHealth(), enemyAttack / 10);
            assertEquals(round.getDeltaEnemyHealth(), playerAttack / 5);
            enemyHealth -= round.getDeltaEnemyHealth();
            playerHealth -= round.getDeltaCharacterHealth();
        }

        if (enemyDies) {
            assertTrue(enemyHealth <= 0);
        } else {
            assertTrue(playerHealth <= 0);
        }
    }
    @Test
    public void testBattleWithSpiderWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithSpiderLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithZombieWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithZombieLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithMerceariesWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithMerceariesLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyInvincibilityPotionWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyInvincibilityPotionLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyInvisibilityPotionWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyInvisibilityPotionLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyBowWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyBowLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyShieldWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyShieldGtEnemyAttack() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemyShieldLost() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordAndBowWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordAndBowLost() {
                DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordAndBowAndShieldWin() {
        DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
    @Test
    public void testBattleWithEnemySwordAndBowAndShieldLost() {
                DungeonManiaController controller = new DungeonManiaController();
        DungeonResponse postBattleResponse = genericMercenarySequence(controller,
                "c_battleTests_basicMercenaryPlayerDies");
    }
}
