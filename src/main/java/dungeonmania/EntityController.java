package dungeonmania;

import org.json.JSONObject;

import dungeonmania.StaticEntities.Exit;
import dungeonmania.helpers.Config;

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
        }
        return null;
    }
}
