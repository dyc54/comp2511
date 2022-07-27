package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.inventories.Inventory;
import dungeonmania.inventories.InventoryViewer;
/**
 * Save buildable Recipe
 */
public class BuildableRecipe implements BuildableComponent{
    List<BuildableComponent> and;
    List<BuildableComponent> or;
    BuildableComponent replace;
    BuildablePrerequisite prerequisite;
    String recipeName;
    boolean isSatisfied;
    public BuildableRecipe(String recipeName) {
        this.recipeName = recipeName;
        replace = null;
        prerequisite = new BuildablePrerequisite();
        and = new ArrayList<>();
        or = new ArrayList<>();
    }
    private boolean isSatisfied(InventoryViewer inventory) {
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
    private BuildableRecipe consumeMaterial(Inventory inventory) {
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
    public BuildableRecipe attachPrerequisite(BuildablePrerequisite prerequisite) {
        this.prerequisite = prerequisite;
        return this;
    }
    public BuildablePrerequisite getPrerequisite() {
        return prerequisite;
    }
    @Override
    public String getItemType() {
        // TODO Auto-generated method stub
        return getRecipeName();
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
    public BuildableComponent CountItem(InventoryViewer inventory) {
        // TODO Auto-generated method stub
        isSatisfied = this.isSatisfied(inventory);
        if (!isSatisfied && hasReplacement()) {
            replace.CountItem(inventory);
        }
        return this;
    }
    @Override
    public BuildableComponent removeCountItem(Inventory inventory) {
        if (hasReplacement() && replace.isSatisfied()) {
            replace.removeCountItem(inventory);
        } else {
            consumeMaterial(inventory);
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
