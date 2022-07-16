package dungeonmania;

import dungeonmania.collectableEntities.durabilityEntities.BuildableEntities.Bow;
import dungeonmania.collectableEntities.durabilityEntities.BuildableEntities.BuildableRecipe;
import dungeonmania.collectableEntities.durabilityEntities.BuildableEntities.Shield;
import dungeonmania.helpers.Config;
import dungeonmania.inventories.Inventory;

public class BuildableEntityFactory extends EntityFactory{
    public static boolean hasSufficientItems(String type, Inventory inventory) {
        switch (type) {
            case "bow":
                boolean wood_b = inventory.hasItem("wood", 1);
                boolean arrow = inventory.hasItem("arrow", 3);
                return wood_b && arrow;
            case "shield":
                boolean wood_s = inventory.hasItem("wood", 2);
                boolean treasure  = inventory.hasItem("treasure", 1);
                boolean key  = inventory.hasItem("key", 1);
                return wood_s && (treasure || key);
            default:
                break;
        }
        return false;
    }
    public static Entity newEntity(String type, Config config, Inventory inventory) {
        switch (type) {
            case "bow":
                inventory.removeFromInventoryList("wood", 1);
                inventory.removeFromInventoryList("arrow", 3);
                return new Bow(type, config.bow_durability - 1);
            case "shield":
                inventory.removeFromInventoryList("wood", 2);

                return new Shield(type, config.shield_defence, config.shield_durability - 1);
            default:
                break;
        }
        return null;
    }
    public static Entity newEntity(String type, Config config) {
        switch (type) {
            case "bow":
                return new Bow(type, config.bow_durability);
            case "shield":
                return new Shield(type, config.shield_defence, config.shield_durability);
            default:
                break;
        }
        return null;
    }
    public static BuildableRecipe newRecipe(String type) throws IllegalArgumentException {
        switch (type) {
            case "bow":
                BuildableRecipe bow = new BuildableRecipe(type);
                return bow.addAnd("wood", 1).addAnd("arrow", 3);
            case "shield":
                BuildableRecipe shield = new BuildableRecipe(type);
                return shield.addAnd("wood", 2).addOr("key", 1).addOr("treasure", 1);
            default:
                throw new IllegalArgumentException(String.format("buildable (%s) is not one of bow, shield", type));
        }
    }
}
