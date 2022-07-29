package dungeonmania.helpers;
import java.io.IOException;

import org.json.*;
/**
 * load config
 */
public class Config {
    public final double ally_attack;
    public final double ally_defence;
    public final int bomb_radius;
    public final int bow_durability;
    public final int bribe_amount;
    public final int bribe_radius;
    public final int enemy_goal;
    public final int invincibility_potion_duration;
    public final int invisibility_potion_duration;
    public final double mercenary_attack;
    public final double mercenary_health;
    public final double player_attack;
    public final double player_health;
    public final double shield_defence;
    public final int shield_durability;
    public final double spider_attack;
    public final double spider_health;
    public final int spider_spawn_rate;
    public final double sword_attack;
    public final int sword_durability;
    public final int treasure_goal;
    public final double zombie_attack;
    public final double zombie_health;
    public final int zombie_spawn_rat;
    // Milestone3 added
    public final double assassin_attack;
    public final int assassin_bribe_amount;
    public final double assassin_bribe_fail_rate;
    public final double assassin_health;
    public final int assassin_recon_radius;
    public final double hydra_attack;
    public final double hydra_health;
    public final double hydra_spawn_rate;
    public final double hydra_health_increase_rate;
    public final double hydra_health_increase_amount;
    public final int mind_control_duration;
    public final double midnight_armour_attack;
    public final double midnight_armour_defence;
    
    public Config(String FileName) throws IOException {
        String content = FileReader.LoadFile(String.format(FileName));
        JSONObject json =  new JSONObject(content);
        ally_attack = json.getDouble("ally_attack");
        ally_defence = json.getDouble("ally_defence");
        bomb_radius = json.getInt("bomb_radius");
        bow_durability = json.getInt("bow_durability");
        bribe_amount = json.getInt("bribe_amount");
        bribe_radius = json.getInt("bribe_radius");
        enemy_goal = json.getInt("enemy_goal");
        invincibility_potion_duration = json.getInt("invincibility_potion_duration");
        invisibility_potion_duration = json.getInt("invisibility_potion_duration");
        mercenary_attack = json.getDouble("mercenary_attack");
        mercenary_health = json.getDouble("mercenary_health");
        player_attack  = json.getDouble("player_attack");
        player_health  = json.getDouble("player_health");
        shield_defence = json.getDouble("shield_defence");
        shield_durability = json.getInt("shield_durability");
        spider_attack = json.getDouble("spider_attack");
        spider_health = json.getDouble("spider_health");
        spider_spawn_rate = json.getInt("spider_spawn_rate");
        sword_attack = json.getDouble("sword_attack");
        sword_durability = json.getInt("sword_durability");
        treasure_goal = json.getInt("treasure_goal");
        zombie_attack = json.getDouble("zombie_attack");
        zombie_health = json.getDouble("zombie_health");
        zombie_spawn_rat = json.getInt("zombie_spawn_rate");
        double assassin_attack;
        int assassin_bribe_amount;
        double assassin_bribe_fail_rate;
        double assassin_health;
        int assassin_recon_radius;
        int hydra_spawn_rate;
        double hydra_attack;
        double hydra_health;
        double hydra_health_increase_rate;
        double hydra_health_increase_amount;
        int mind_control_duration;
        double midnight_armour_attack;
        double midnight_armour_defence;

        // * to be compatible with m2 config files.
        try {
            assassin_attack = json.getDouble("assassin_attack");
            assassin_bribe_amount = json.getInt("assassin_bribe_amount");
            assassin_bribe_fail_rate = json.getDouble("assassin_bribe_fail_rate");
            assassin_health = json.getDouble("assassin_health");
            assassin_recon_radius = json.getInt("assassin_recon_radius");
            hydra_spawn_rate = 0;
            hydra_attack = json.getDouble("hydra_attack");
            hydra_health = json.getDouble("hydra_health");
            hydra_health_increase_rate = json.getDouble("hydra_health_increase_rate");
            hydra_health_increase_amount = json.getDouble("hydra_health_increase_amount");
            mind_control_duration = json.getInt("mind_control_duration");
            midnight_armour_attack = json.getDouble("midnight_armour_attack");
            midnight_armour_defence = json.getDouble("midnight_armour_defence");
        } catch (JSONException exp) {
            assassin_attack = 0;
            assassin_bribe_amount = 0;
            assassin_bribe_fail_rate = 0;
            assassin_health = 0;
            assassin_recon_radius = 0;
            hydra_spawn_rate = 0;
            hydra_attack = 0;
            hydra_health = 0;
            hydra_health_increase_rate = 0;
            hydra_health_increase_amount = 0;
            mind_control_duration = 0;
            midnight_armour_attack = 0;
            midnight_armour_defence = 0;
        }
        this.assassin_attack = assassin_attack;
        this.assassin_bribe_amount = assassin_bribe_amount;
        this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
        this.assassin_health = assassin_health;
        this.assassin_recon_radius = assassin_recon_radius;
        this.hydra_spawn_rate = hydra_spawn_rate;
        this.hydra_attack = hydra_attack;
        this.hydra_health = hydra_health;
        this.hydra_health_increase_rate = hydra_health_increase_rate;
        this.hydra_health_increase_amount = hydra_health_increase_amount;
        this.mind_control_duration = mind_control_duration;
        this.midnight_armour_attack = midnight_armour_attack;
        this.midnight_armour_defence = midnight_armour_defence;
    }
}
