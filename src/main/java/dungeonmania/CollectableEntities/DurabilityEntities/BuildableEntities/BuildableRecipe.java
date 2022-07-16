package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dungeonmania.Inventories.Inventory;

public class BuildableRecipe {
    // BuildableRecipeTree and;
    List<BuildableComponent> and;
    List<BuildableComponent> or;
    // BuildableRecipeTree or;
    // int andSize;
    // int orSize;
    String recipeName;
    public BuildableRecipe(String recipeName) {
        this.recipeName = recipeName;
        and = new ArrayList<>();
        or = new ArrayList<>();
    }
    public boolean isSatisfied(Inventory inventory) {
        System.out.println(String.format("To build %s: ", recipeName));
        System.out.println("Checking and branch");
        and.stream().forEach(component -> component.CountItem(inventory));
        Boolean.valueOf(true).booleanValue();
        boolean andcondi = and.stream().map(component -> Boolean.valueOf(component.isSatisfied())).allMatch(component -> component.booleanValue());
        System.out.println("Checking or branch");
        or.stream().forEach(component -> component.CountItem(inventory));
        boolean orcondi = or.stream().map(component -> Boolean.valueOf(component.isSatisfied())).anyMatch(component -> component.booleanValue());
        boolean a = andcondi || and.size() == 0;
        boolean o = orcondi || or.size() == 0;
        System.out.println(String.format("To build %s: AND branch %s, OR branch %s -> %s", recipeName, a, o, a && o));
        return a && o;
    }
    public BuildableRecipe addAnd(String type, int amount) {
        and.add(new BuildableRecipematerial(type, amount));
        return this;
    }
    public BuildableRecipe addOr(String type, int amount) {
        or.add(new BuildableRecipematerial(type, amount));
        return this;
    }
    public BuildableRecipe consumeMaterial(Inventory inventory) {
        and.stream().forEach(material -> material.removeCountItem(inventory));
        if (or.size() != 0) {
            BuildableComponent consumedItem = or.stream().filter(material -> inventory.hasItem(material.getItemType(), material.getItemAmount())).findFirst().get();
            consumedItem.removeCountItem(inventory);
        }
        return this;
    }
    public String getRecipeName() {
        return recipeName;
    }
}
