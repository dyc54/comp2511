package dungeonmania;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import dungeonmania.CollectableEntities.*;
import dungeonmania.CollectableEntities.DurabilityEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.DurabilityEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.DurabilityEntities.Sword;
import dungeonmania.StaticEntities.*;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.MovingEntities.Mercenary;
import dungeonmania.MovingEntities.Spider;
import dungeonmania.MovingEntities.ZombieToast;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.Location;

public class EntityController {

    public Entity newEntity(JSONObject entity, Config config, DungeonMap map) {
        String type = entity.getString("type");
        String id = createId(type);
        int x = entity.getInt("x");
        int y = entity.getInt("y");
        // TODO: Add create entities.

        switch (type) {
            case "player":
                return new Player(id, type, x, y, config.player_attack, config.player_health, map);
            case "exit":
                return new Exit(type, x, y);
            case "boulder":
                return new Boulder(type, x, y);
            case "door":
                return new Door(type, x, y);
            case "portal":
                return new Portal(type, x, y, entity.getString("colour"));
            case "switch":
                return new FloorSwitch(type, x, y);
            case "zombie_toast_spawner":
                return new ZombieToastSpawner(type, x, y, config.zombie_spawn_rat, config.zombie_attack,
                        config.zombie_health, map);
            case "key":
                return new Key(id, type, x, y);
            case "spider":
                return new Spider(id, type, Location.AsLocation(x, y), config.spider_attack, config.spider_health);
            case "wall":
                return new Wall(type, Location.AsLocation(x, y));
            case "arrow":
                return new Arrows(id, type, x, y, config.bow_durability);
            case "bomb":
                return new Bomb(id, type, x, y, config.bomb_radius);
            case "invincibility_potion":
                return new InvincibilityPotion(id, type, x, y, config.invincibility_potion_duration);
            case "invisibility_potion":
                return new InvisibilityPotion(id, type, x, y, config.invisibility_potion_duration);
            case "sword":
                return new Sword(id, type, x, y, config.sword_attack, config.sword_durability);
            case "treasure":
                return new Treasure(id, type, x, y);
            case "wood":
                return new Wood(id, type, x, y, config.shield_durability, config.shield_defence);
            case "zombie_toast":
                return new ZombieToast(id, type, Location.AsLocation(x, y), config.zombie_attack, config.zombie_health);
            case "mercenary":
                return new Mercenary(type, Location.AsLocation(x, y), config.mercenary_attack, config.mercenary_health);
        }
        return null;
    }

    public static String createId(String type) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        return type + sdf.format(System.currentTimeMillis());
    }
}
