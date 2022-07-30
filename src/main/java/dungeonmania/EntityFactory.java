package dungeonmania;

import org.json.JSONObject;

import dungeonmania.bosses.Assassin;
import dungeonmania.bosses.Hydra;
import dungeonmania.collectableEntities.*;
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
                return new Player(type, x, y, config.playerAttack, config.playerHealth, map);
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
                return new ZombieToastSpawner(type, x, y, config.zombieSpawnRat, config.zombieAttack,
                        config.zombieHealth, map);
            case "key":
                key = entity.getInt("key");
                return new Key(type, x, y, key);
            case "spider":
                return new Spider(type, Location.AsLocation(x, y), config.spiderAttack, config.spiderHealth);
            case "wall":
                return new Wall(type, Location.AsLocation(x, y));
            case "arrow":
                return new Arrows(type, x, y);
            case "bomb":
                if (entity.has("logic")) {
                    return new LogicBomb(type, x, y, config.bombRadius, map.getTimer(),
                                        entity.getString("logic"), new DungeonMapWirteDecorator(map));
                }
                return new Bomb(type, x, y, config.bombRadius, new DungeonMapWirteDecorator(map));
            case "invincibility_potion":
                return new InvincibilityPotion(type, config.invincibilityPotionDuration, x, y);
            case "invisibility_potion":
                return new InvisibilityPotion(type, config.invisibilityPotionDuration, x, y);
            case "sword":
                return new Sword(type, x, y, config.swordDurability, config.swordAttack);
            case "treasure":
                return new Treasure(type, x, y);
            case "wood":
                return new Wood(type, x, y);
            case "zombie_toast":
                return new ZombieToast(type, Location.AsLocation(x, y), config.zombieAttack, config.zombieHealth);
            case "mercenary":
                MercenaryEnemy mercenary = new MercenaryEnemy(type, Location.AsLocation(x, y), config.mercenaryAttack, config.mercenaryHealth, config.bribeAmount, config.bribeRadius, config.allyAttack, config.allyDefence);
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
                Assassin assassin = new Assassin(type, Location.AsLocation(x, y), config.assassinHealth, config.assassinAttack, config.assassinBribeAmount, config.bribeRadius, config.allyAttack, config.allyDefence, config.assassinBribeFailRate, config.assassinReconRadius);
                map.getPlayer().attach(assassin);
                return assassin;
            case "hydra":
                return new Hydra(type, Location.AsLocation(x, y), config.hydraHealth, config.hydraAttack, config.hydraHealthIncreaseRate, config.hydraHealthIncreaseAmount);
        }
        return null;
    }

}
