package dungeonmania.helpers;
import java.io.IOException;

import org.json.*;
/**
 * load config
 */
public class Config {
    public final int ally_attack;
    public final int ally_defence;
    public final int bomb_radius;
    public final int bow_durability;
    public final int bribe_amount;
    public final int bribe_radius;
    public final int enemy_goal;
    public final int invincibility_potion_duration;
    public final int invisibility_potion_duration;
    public final int mercenary_attack;
    public final int mercenary_health;
    public final int player_attack;
    public final int player_health;
    public final int shield_defence;
    public final int shield_durability;
    public final int spider_attack;
    public final int spider_health;
    public final int spider_spawn_rate;
    public final int sword_attack;
    public final int sword_durability;
    public final int treasure_goal;
    public final int zombie_attack;
    public final int zombie_health;
    public final int zombie_spawn_rat;
    // Milestone3 added
    public final int assassin_attack;
    public final int assassin_bribe_amount;
    public final int assassin_bribe_fail_rate;
    public final int assassin_health;
    public final int assassin_recon_radius;
    public final int hydra_attack;
    public final int hydra_health;
    public final int hydra_spawn_rate;
    public final double hydra_health_increase_rate;
    public final int hydra_health_increase_amount;
    public final int mind_control_duration;
    public final int midnight_armour_attack;
    public final int midnight_armour_defence;
    
    public Config(String FileName) throws IOException {
        String content = FileReader.LoadFile(String.format(FileName));
        JSONObject json =  new JSONObject(content);
        ally_attack = json.getInt("ally_attack");
        ally_defence = json.getInt("ally_defence");
        bomb_radius = json.getInt("bomb_radius");
        bow_durability = json.getInt("bow_durability");
        bribe_amount = json.getInt("bribe_amount");
        bribe_radius = json.getInt("bribe_radius");
        enemy_goal = json.getInt("enemy_goal");
        invincibility_potion_duration = json.getInt("invincibility_potion_duration");
        invisibility_potion_duration = json.getInt("invisibility_potion_duration");
        mercenary_attack = json.getInt("mercenary_attack");
        mercenary_health = json.getInt("mercenary_health");
        player_attack  = json.getInt("player_attack");
        player_health  = json.getInt("player_health");
        shield_defence = json.getInt("shield_defence");
        shield_durability = json.getInt("shield_durability");
        spider_attack = json.getInt("spider_attack");
        spider_health = json.getInt("spider_health");
        spider_spawn_rate = json.getInt("spider_spawn_rate");
        sword_attack = json.getInt("sword_attack");
        sword_durability = json.getInt("sword_durability");
        treasure_goal = json.getInt("treasure_goal");
        zombie_attack = json.getInt("zombie_attack");
        zombie_health = json.getInt("zombie_health");
        zombie_spawn_rat = json.getInt("zombie_spawn_rate");
        int assassin_attack;
        int assassin_bribe_amount;
        int assassin_bribe_fail_rate;
        int assassin_health;
        int assassin_recon_radius;
        int hydra_spawn_rate;
        int hydra_attack;
        int hydra_health;
        int hydra_health_increase_rate;
        int hydra_health_increase_amount;
        int mind_control_duration;
        int midnight_armour_attack;
        int midnight_armour_defence;

        // * to be compatible with m2 config files.
        try {
            assassin_attack = json.getInt("assassin_attack");
            assassin_bribe_amount = json.getInt("assassin_bribe_amount");
            assassin_bribe_fail_rate = json.getInt("assassin_bribe_fail_rate");
            assassin_health = json.getInt("assassin_health");
            assassin_recon_radius = json.getInt("assassin_recon_radius");
            hydra_spawn_rate = 0;
            hydra_attack = json.getInt("hydra_attack");
            hydra_health = json.getInt("hydra_health");
            hydra_health_increase_rate = json.getInt("hydra_health_increase_rate");
            hydra_health_increase_amount = json.getInt("hydra_health_increase_amount");
            mind_control_duration = json.getInt("mind_control_duration");
            midnight_armour_attack = json.getInt("midnight_armour_attack");
            midnight_armour_defence = json.getInt("midnight_armour_defence");
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
