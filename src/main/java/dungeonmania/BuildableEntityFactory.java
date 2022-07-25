package dungeonmania;

import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.Bow;
import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.BuildableComponent;
import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.BuildableRecipe;
import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.Shield;
import dungeonmania.helpers.Config;

public class BuildableEntityFactory {
    
    public static Entity newEntity(String type, Config config, String id) {
        switch (type) {
            case "bow":
                return new Bow(id, type, config.bow_durability);
            case "shield":
                return new Shield(type, config.shield_defence, config.shield_durability, id);
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
            case "midnight_armour":
                BuildableRecipe armour = new BuildableRecipe(type);
                return armour.addAnd("sword", 1).addAnd("sun_stone", 1);
            case "sceptre":
                BuildableRecipe sceptre = new BuildableRecipe(type);
                BuildableRecipe pair1 = new BuildableRecipe("pair1");
                pair1.addOr("wood", 1).addOr("arrow", 2);
                BuildableRecipe pair2 = new BuildableRecipe("pair1");
                pair2.addOr("key", 1).addOr("treasure", 1);
                return sceptre.addAnd(pair1).addAnd(pair2).addAnd("sun_stone", 1);
            default:
                throw new IllegalArgumentException(String.format("buildable (%s) is not one of bow, shield", type));
        }
    }
}
