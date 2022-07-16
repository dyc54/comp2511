package dungeonmania.helpers;
import java.io.IOException;

import org.json.*;

public class Config {
    // public 
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
    }
}
