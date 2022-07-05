package dungeonmania;

import org.json.JSONObject;

import dungeonmania.MovingEntities.Spider;
import dungeonmania.StaticEntities.Exit;
import dungeonmania.StaticEntities.Wall;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.Location;

public class EntityController {
    public static Entity newEntity(JSONObject entity, Config config) {
        String type = entity.getString("type");
        int x = entity.getInt("x");
        int y = entity.getInt("y");
        // TODO: Add create entities.

        switch(type){
            case "player":
            return new Player(type,x, y, config.player_attack, config.player_health);
            case "exit":
            return new Exit(type,x,y);
            case "spider":
            return new Spider(type, Location.AsLocation(x, y), config.spider_attack, config.spider_health);
            case "wall":
            return new Wall(type, Location.AsLocation(x, y));
        }
        return null;
    }
}
