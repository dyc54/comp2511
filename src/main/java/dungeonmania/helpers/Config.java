package dungeonmania.helpers;
import java.io.IOException;

import org.json.*;
/**
 * load config
 */
public class Config {
    public final double allyAttack;
    public final double allyDefence;
    public final int bombRadius;
    public final int bowDurability;
    public final int bribeAmount;
    public final int bribeRadius;
    public final int enemyGoal;
    public final int invincibilityPotionDuration;
    public final int invisibilityPotionDuration;
    public final int mercenaryAttack;
    public final int mercenaryHealth;
    public final int playerAttack;
    public final int playerHealth;
    public final int shieldDefence;
    public final int shieldDurability;
    public final int spiderAttack;
    public final int spiderHealth;
    public final int spiderSpawnRate;
    public final int swordAttack;
    public final int swordDurability;
    public final int treasureGoal;
    public final int zombieAttack;
    public final int zombieHealth;
    public final int zombieSpawnRat;
    // Milestone3 added
    public final double assassinAttack;
    public final int assassinBribeAmount;
    public final double assassinBribeFailRate;
    public final double assassinHealth;
    public final int assassinReconRadius;
    public final int hydraAttack;
    public final int hydraHealth;
    public final int hydraSpawnRate;
    public final double hydraHealthIncreaseRate;
    public final int hydraHealthIncreaseAmount;
    public final int mindControlDuration;
    public final double midnightArmourAttack;
    public final double midnightArmourDefence;
    
    public Config(String FileName) throws IOException {
        String content = FileReader.LoadFile(String.format(FileName));
        JSONObject json =  new JSONObject(content);
        allyAttack = json.getDouble("ally_attack");
        allyDefence = json.getDouble("ally_defence");
        bombRadius = json.getInt("bomb_radius");
        bowDurability = json.getInt("bow_durability");
        bribeAmount = json.getInt("bribe_amount");
        bribeRadius = json.getInt("bribe_radius");
        enemyGoal = json.getInt("enemy_goal");
        invincibilityPotionDuration = json.getInt("invincibility_potion_duration");
        invisibilityPotionDuration = json.getInt("invisibility_potion_duration");
        mercenaryAttack = json.getInt("mercenary_attack");
        mercenaryHealth = json.getInt("mercenary_health");
        playerAttack  = json.getInt("player_attack");
        playerHealth  = json.getInt("player_health");
        shieldDefence = json.getInt("shield_defence");
        shieldDurability = json.getInt("shield_durability");
        spiderAttack = json.getInt("spider_attack");
        spiderHealth = json.getInt("spider_health");
        spiderSpawnRate = json.getInt("spider_spawn_rate");
        swordAttack = json.getInt("sword_attack");
        swordDurability = json.getInt("sword_durability");
        treasureGoal = json.getInt("treasure_goal");
        zombieAttack = json.getInt("zombie_attack");
        zombieHealth = json.getInt("zombie_health");
        zombieSpawnRat = json.getInt("zombie_spawn_rate");
        int assassinAttack;
        int assassinBribeAmount;
        double assassinBribeFailRate;
        int assassinHealth;
        int assassinReconRadius;
        int hydraSpawnRate;
        int hydraAttack;
        int hydraHealth;
        double hydraHealthIncreaseRate;
        int hydraHealthIncreaseAmount;
        int mindControlDuration;
        int midnightArmourAttack;
        int midnightArmourDefence;

        // * to be compatible with m2 config files.
        try {
            assassinAttack = json.getInt("assassin_attack");
            assassinBribeAmount = json.getInt("assassin_bribe_amount");
            assassinBribeFailRate = json.getDouble("assassin_bribe_fail_rate");
            assassinHealth = json.getInt("assassin_health");
            assassinReconRadius = json.getInt("assassin_recon_radius");
            hydraSpawnRate = 0;
            hydraAttack = json.getInt("hydra_attack");
            hydraHealth = json.getInt("hydra_health");
            hydraHealthIncreaseRate = json.getDouble("hydra_health_increase_rate");
            hydraHealthIncreaseAmount = json.getInt("hydra_health_increase_amount");
            mindControlDuration = json.getInt("mind_control_duration");
            midnightArmourAttack = json.getInt("midnight_armour_attack");
            midnightArmourDefence = json.getInt("midnight_armour_defence");
        } catch (JSONException exp) {
            assassinAttack = 0;
            assassinBribeAmount = 0;
            assassinBribeFailRate = 0;
            assassinHealth = 0;
            assassinReconRadius = 0;
            hydraSpawnRate = 0;
            hydraAttack = 0;
            hydraHealth = 0;
            hydraHealthIncreaseRate = 0;
            hydraHealthIncreaseAmount = 0;
            mindControlDuration = 0;
            midnightArmourAttack = 0;
            midnightArmourDefence = 0;
        }
        this.assassinAttack = assassinAttack;
        this.assassinBribeAmount = assassinBribeAmount;
        this.assassinBribeFailRate = assassinBribeFailRate;
        this.assassinHealth = assassinHealth;
        this.assassinReconRadius = assassinReconRadius;
        this.hydraSpawnRate = hydraSpawnRate;
        this.hydraAttack = hydraAttack;
        this.hydraHealth = hydraHealth;
        this.hydraHealthIncreaseRate = hydraHealthIncreaseRate;
        this.hydraHealthIncreaseAmount = hydraHealthIncreaseAmount;
        this.mindControlDuration = mindControlDuration;
        this.midnightArmourAttack = midnightArmourAttack;
        this.midnightArmourDefence = midnightArmourDefence;
    }
}
