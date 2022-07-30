package dungeonmania;

import dungeonmania.buildableEntities.Bow;
import dungeonmania.buildableEntities.BuildablePrerequisite;
import dungeonmania.buildableEntities.BuildableRecipe;
import dungeonmania.buildableEntities.BuildableRecipeReplacement;
import dungeonmania.buildableEntities.BuildableRecipematerial;
import dungeonmania.buildableEntities.MidnightArmour;
import dungeonmania.buildableEntities.Sceptre;
import dungeonmania.buildableEntities.Shield;
import dungeonmania.helpers.Config;
import dungeonmania.movingEntities.ZombieToast;

public class BuildableEntityFactory {
    
    public static Entity newEntity(String type, Config config, String id) {
        switch (type) {
            case "bow":
                return new Bow(id, type, config.bowDurability);
            case "shield":
                return new Shield(type, config.shieldDefence, config.shieldDurability, id);
            case "midnight_armour":
                return new MidnightArmour(type, config.midnightArmourAttack, config.midnightArmourDefence, id);
            case "sceptre":
                return new Sceptre(type, config.mindControlDuration, id);
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
                BuildablePrerequisite prerequisite = new BuildablePrerequisite();
                prerequisite.attach(entity -> !(entity instanceof ZombieToast));
                return armour.addAnd("sword", 1).addAnd("sun_stone", 1).attachPrerequisite(prerequisite);
            case "sceptre":
                BuildableRecipe sceptre = new BuildableRecipe(type);
                BuildableRecipe pair1 = new BuildableRecipe("pair1");
                pair1.addOr("wood", 1).addOr("arrow", 2);
                BuildableRecipematerial require2 = new BuildableRecipematerial("sun_stone", 1, false);
                BuildableRecipe pair2 = new BuildableRecipe("pair2");
                pair2.addOr("key", 1).addOr("treasure", 1);
                require2.setReplacement(new BuildableRecipeReplacement(pair2, true));
                return sceptre.addAnd(pair1).addAnd("sun_stone", 1).addAnd(require2);
            default:
                throw new IllegalArgumentException(String.format("buildable (%s) is not one of bow, shield", type));
        }
    }
}
