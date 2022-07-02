package dungeonmania;

import org.json.JSONObject;

import dungeonmania.helpers.Config;

public class EntityController {
    public static Entity newEntity(JSONObject entity, Config config) {
        String type = entity.getString("type");
        int x = entity.getInt("x");
        int y = entity.getInt("y");
        // TODO: Add create entities.
        return null;
    }
}
