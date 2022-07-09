package dungeonmania;

import org.json.JSONObject;

import dungeonmania.CollectableEntities.*;
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
    public static Entity newEntity(JSONObject entity, Config config, DungeonMap map) {
        String type = entity.getString("type");
        int x = entity.getInt("x");
        int y = entity.getInt("y");
        // TODO: Add create entities.

        switch (type) {
            case "player":
                return new Player(type, x, y, config.player_attack, config.player_health, map);
            case "exit":
                return new Exit(type, x, y);
            case "boulder":
                return new Boulder(type, x, y);
            case "door":
                return new Door(type, x, y);
            case "portal":
                return new Portal(type, x, y);
            case "switch":
                return new FloorSwitch(type, x, y);
            case "zombie_toast_spawner":
                return new ZombieToastSpawner(type, x, y, config.zombie_spawn_rat);
            case "key":
                return new Key(type, x, y);
            case "spider":
                return new Spider(type, Location.AsLocation(x, y), config.spider_attack, config.spider_health);
            case "wall":
                return new Wall(type, Location.AsLocation(x, y));
            case "zombie":
                return new ZombieToast(type, Location.AsLocation(x, y), config.zombie_attack, config.zombie_health);
            case "mercenary":
                return new Mercenary(type, Location.AsLocation(x, y), config.mercenary_attack, config.mercenary_health);
        }
        return null;
    }
}
