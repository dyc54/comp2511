package dungeonmania;

import org.json.JSONObject;

import dungeonmania.bosses.Assassin;
import dungeonmania.bosses.Hydra;
import dungeonmania.collectableEntities.*;
import dungeonmania.collectableEntities.durabilityEntities.InvincibilityPotion;
import dungeonmania.collectableEntities.durabilityEntities.InvisibilityPotion;
import dungeonmania.collectableEntities.durabilityEntities.Sword;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.MercenaryEnemy;
import dungeonmania.movingEntities.Spider;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.staticEntities.*;
/**
 * create Entity
 */
public class EntityFactory {
    public static Entity newEntity(JSONObject entity, Config config, DungeonMap map) {
        // System.out.println(entity.toString());
        if (entity.has("id")) {
            // System.out.println("branch 1");
            Entity entity2 = newEntities(entity, config, map);
            entity2.setEntityId(entity.getString("id"));
            return entity2;
        } else {
            // System.out.println("branch 2");
            return newEntities(entity, config, map);
        }
        
    }
    private static Entity newEntities(JSONObject entity, Config config, DungeonMap map) {
        String type = entity.getString("type");
        int x = entity.getInt("x");
        int y = entity.getInt("y");
        System.out.println(String.format("new entity %s at <%d,%d>", type, x, y));
        int key = 0;
        switch (type) {
            case "player":
                return new Player(type, x, y, config.player_attack, config.player_health, map);
            case "exit":
                return new Exit(type, x, y);
            case "boulder":
                return new Boulder(type, x, y);
            case "door":
                key = entity.getInt("key");
                return new Door(type, x, y, key);
            case "portal":
                String colour = entity.getString("colour");
                return new Portal(type, x, y, colour);
            case "switch":
                return new FloorSwitch(type, x, y);
            case "zombie_toast_spawner":
                return new ZombieToastSpawner(type, x, y, config.zombie_spawn_rat, config.zombie_attack,
                        config.zombie_health, map);
            case "key":
                key = entity.getInt("key");
                return new Key(type, x, y, key);
            case "spider":
                return new Spider(type, Location.AsLocation(x, y), config.spider_attack, config.spider_health);
            case "wall":
                return new Wall(type, Location.AsLocation(x, y));
            case "arrow":
                return new Arrows(type, x, y);
            case "bomb":
                return new Bomb(type, x, y, config.bomb_radius);
            case "invincibility_potion":
                return new InvincibilityPotion(type, config.invincibility_potion_duration, x, y);
            case "invisibility_potion":
                return new InvisibilityPotion(type, config.invisibility_potion_duration, x, y);
            case "sword":
                return new Sword(type, x, y, config.sword_durability, config.sword_attack);
            case "treasure":
                return new Treasure(type, x, y);
            case "wood":
                return new Wood(type, x, y);
            case "zombie_toast":
                return new ZombieToast(type, Location.AsLocation(x, y), config.zombie_attack, config.zombie_health);
            case "mercenary":
                MercenaryEnemy mercenary = new MercenaryEnemy(type, Location.AsLocation(x, y), config.mercenary_attack, config.mercenary_health, config.bribe_amount, config.bribe_radius, config.ally_attack, config.ally_defence);
                map.getPlayer().attach(mercenary);
                return mercenary;
            case "swamp_tile":
                break;
            case "sun_stone":
                return new SunStone(type, x, y);
            case "time_turner":
                break;
            case "time_travelling_portal":
                break;
            case "light_bulb_on":
                break;
            case "wire":
                break;
            case "switch_door":
                break;
            case "assassin":
                return new Assassin(type, Location.AsLocation(x, y), config.assassin_health, config.assassin_attack, config.bribe_amount, config.bribe_radius, config.ally_attack, config.ally_defence);
            case "hydra":
                return new Hydra(type, Location.AsLocation(x, y), config.hydra_health, config.hydra_attack);


        }
        return null;
    }

}
