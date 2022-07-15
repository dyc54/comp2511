package dungeonmania;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.json.JSONObject;

import dungeonmania.CollectableEntities.*;
import dungeonmania.CollectableEntities.DurabilityEntities.InvincibilityPotion;
import dungeonmania.CollectableEntities.DurabilityEntities.InvisibilityPotion;
import dungeonmania.CollectableEntities.DurabilityEntities.Sword;
import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.Bow;
import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.Shield;
import dungeonmania.StaticEntities.*;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.MovingEntities.Mercenary;
import dungeonmania.MovingEntities.MercenaryAlly;
import dungeonmania.MovingEntities.Spider;
import dungeonmania.MovingEntities.ZombieToast;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.Location;

public class EntityFactory {

    public static Entity newEntity(JSONObject entity, Config config, DungeonMap map) {
        String type = entity.getString("type");
        int x = entity.getInt("x");
        int y = entity.getInt("y");
        // TODO: Add create entities.
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
                return new Sword(type, x, y, config.sword_durability - 1, config.sword_attack);
            case "treasure":
                return new Treasure(type, x, y);
            case "wood":
                return new Wood(type, x, y);
            case "zombie_toast":
                return new ZombieToast(type, Location.AsLocation(x, y), config.zombie_attack, config.zombie_health);
            case "mercenary":
                return new Mercenary(type, Location.AsLocation(x, y), config.mercenary_attack, config.mercenary_health, config.bribe_amount, config.bribe_radius, config.ally_attack, config.ally_defence, false);
            // case "ally":
            //     return new MercenaryAlly(type, Location.AsLocation(x, y), config.ally_attack, config.ally_defence, 0);
        }
        return null;
    }

}
