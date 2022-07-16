package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.inventories.Inventory;

public class BuildableRecipe {
    List<BuildableComponent> and;
    List<BuildableComponent> or;
    String recipeName;
    public BuildableRecipe(String recipeName) {
        this.recipeName = recipeName;
        and = new ArrayList<>();
        or = new ArrayList<>();
    }
    public boolean isSatisfied(Inventory inventory) {
        and.stream().forEach(component -> component.CountItem(inventory));
        Boolean.valueOf(true).booleanValue();
        boolean andcondi = and.stream().map(component -> Boolean.valueOf(component.isSatisfied())).allMatch(component -> component.booleanValue());
        or.stream().forEach(component -> component.CountItem(inventory));
        boolean orcondi = or.stream().map(component -> Boolean.valueOf(component.isSatisfied())).anyMatch(component -> component.booleanValue());
        boolean andbranch = andcondi || and.size() == 0;
        boolean orbranch = orcondi || or.size() == 0;
        return andbranch && orbranch;
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
