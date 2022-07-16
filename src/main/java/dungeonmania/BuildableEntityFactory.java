package dungeonmania;

import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.Bow;
import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.BuildableRecipe;
import dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities.Shield;
import dungeonmania.Inventories.Inventory;
import dungeonmania.helpers.Config;

public class BuildableEntityFactory extends EntityFactory{
    
    public static Entity newEntity(String type, Config config) {
        switch (type) {
            case "bow":
                return new Bow(type, config.bow_durability);
            case "shield":
                return new Shield(type, config.shield_defence, config.shield_durability);
            default:
                break;
        }
        throw new IllegalArgumentException(String.format("buildable (%s) is not one of bow, shield", type));
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
