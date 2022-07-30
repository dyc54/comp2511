package dungeonmania.strategies.movementStrategies;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.MovingEntity;

public class MovementOptions {
    /**
     * Decode Location arguments
     * @param location
     * @param argument
     * @return
     */
    public static List<Location> decodeLocationsArguments(Location location, String argument) {
        List<Location> choices = new ArrayList<>(4);
        for (char ch : argument.toCharArray()) {
            switch (ch) {
                case 'u':
                    choices.add(location.getUp());
                    break;
                case 'd':
                    choices.add(location.getDown());
                    break;
                case 'l':
                    choices.add(location.getLeft());
                    break;
                case 'r':
                    choices.add(location.getRight());
                    break;
            }
        }
        return choices;
    }
    
    /**
     * Encode next possible location arguments
     * @param map
     * @param entity
     * @return
     */
    public static String encodeLocationsArguments(DungeonMap map, MovingEntity entity) {
        String possible = "";
        possible += DungeonMap.isaccessible(map, entity.getLocation().getUp(), entity) ? "u": "";
        possible += DungeonMap.isaccessible(map, entity.getLocation().getDown(), entity) ? "d": "";
        possible += DungeonMap.isaccessible(map, entity.getLocation().getLeft(), entity) ? "l": "";
        possible += DungeonMap.isaccessible(map, entity.getLocation().getRight(), entity) ? "r": "";
        return possible;
    }
}
