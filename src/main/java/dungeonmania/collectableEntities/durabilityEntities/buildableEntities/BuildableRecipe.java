package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.inventories.Inventory;
/**
 * Save buildable Recipe
 */
public class BuildableRecipe implements BuildableComponent{
    List<BuildableComponent> and;
    List<BuildableComponent> or;
    BuildableComponent replace;
    String recipeName;
    boolean isSatisfied;
    public BuildableRecipe(String recipeName) {
        this.recipeName = recipeName;
        replace = null;
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
    public BuildableRecipe addAnd(BuildableComponent component) {
        and.add(component);
        return this;
    }
    public BuildableRecipe addOr(BuildableComponent component) {
        or.add(component);
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
    @Override
    public String getItemType() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int getItemAmount() {
        // TODO Auto-generated method stub
        return -1;
    }
    @Override
    public boolean isSatisfied() {
        // TODO Auto-generated method stub
        return (hasReplacement() && replace.isSatisfied()) || isSatisfied;
    }
    @Override
    public BuildableComponent CountItem(Inventory inventory) {
        // TODO Auto-generated method stub
        if (hasReplacement()) {
            replace.CountItem(inventory);
        }
        isSatisfied = this.isSatisfied(inventory);
        return this;
    }
    @Override
    public BuildableComponent removeCountItem(Inventory inventory) {
        Inventory inventoryViewer = inventory.view();
        if (hasReplacement() && replace.isSatisfied()) {
            replace.removeCountItem(inventoryViewer);
        } else {
            consumeMaterial(inventoryViewer);
        }
        return this;
    }
    @Override
    public BuildableComponent setReplacement(BuildableComponent component) {
        // TODO Auto-generated method stub
        replace = component;
        return null;
    }
    @Override
    public boolean hasReplacement() {
        // TODO Auto-generated method stub
        return replace != null;
    }
}
