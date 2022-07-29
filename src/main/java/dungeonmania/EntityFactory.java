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
import dungeonmania.helpers.DungeonMapWirteDecorator;
import dungeonmania.helpers.Location;
import dungeonmania.logicEntities.LightBulb;
import dungeonmania.logicEntities.LogicBomb;
import dungeonmania.logicEntities.LogicSwitch;
import dungeonmania.logicEntities.SwitchDoor;
import dungeonmania.logicEntities.Wire;
import dungeonmania.movingEntities.MercenaryEnemy;
import dungeonmania.movingEntities.Spider;
import dungeonmania.movingEntities.ZombieToast;
import dungeonmania.staticEntities.*;
import dungeonmania.timeTravel.TimeTravellingPortal;
import dungeonmania.timeTravel.TimeTurner;
/**
 * create Entity
 */
public class EntityFactory {
    public static Entity newEntity(JSONObject entity, Config config, DungeonMap map, boolean useId) {
        // System.out.println(entity.toString());
        if (entity.has("id") && useId) {
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
        int factor = 0;
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
                if (entity.has("logic")) {
                    return new LogicSwitch(type, x, y, map.getTimer(), entity.getString("logic"));
                }
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
                if (entity.has("logic")) {
                    return new LogicBomb(type, x, y, config.bomb_radius, map.getTimer(),
                                        entity.getString("logic"), new DungeonMapWirteDecorator(map));
                }
                return new Bomb(type, x, y, config.bomb_radius, new DungeonMapWirteDecorator(map));
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
                int movement_factor = entity.getInt("movement_factor");
                return new SwampTile(type, x, y, movement_factor);
            case "sun_stone":
                return new SunStone(type, x, y);
            case "time_turner":
                return new TimeTurner(type, x, y);
            case "time_travelling_portal":
                return new TimeTravellingPortal(type, Location.AsLocation(x, y));
            case "light_bulb_off":
                return new LightBulb(type, x, y, entity.getString("logic"), map.getTimer());
            case "wire":
                return new Wire(type, x, y, "or", map.getTimer());
            case "switch_door":
                return new SwitchDoor(type, x, y, entity.getInt("key"), entity.getString("logic"), map.getTimer());
            case "assassin":
                Assassin assassin = new Assassin(type, Location.AsLocation(x, y), config.assassin_health, config.assassin_attack, config.assassin_bribe_amount, config.bribe_radius, config.ally_attack, config.ally_defence, config.assassin_bribe_fail_rate, config.assassin_recon_radius);
                map.getPlayer().attach(assassin);
                return assassin;
            case "hydra":
                return new Hydra(type, Location.AsLocation(x, y), config.hydra_health, config.hydra_attack, config.hydra_health_increase_rate, config.hydra_health_increase_amount);
        }
        return null;
    }

}
