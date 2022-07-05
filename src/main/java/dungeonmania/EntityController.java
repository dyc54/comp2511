package dungeonmania;

import org.json.JSONObject;

import dungeonmania.MovingEntities.Spider;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.Location;

public class EntityController {
    public static Entity newEntity(JSONObject entity, Config config) {
        String type = entity.getString("type");
        int x = entity.getInt("x");
        int y = entity.getInt("y");
        // TODO: Add create entities.
        switch(type){
            case "exit":
            return new Spider(Location.AsLocation(x, y), config.spider_attack, config.spider_health);
        }
        return null;
    }
}
