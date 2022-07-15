package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import dungeonmania.Inventories.Inventory;

public class BuildableRecipe {
    BuildableRecipeTree and;
    BuildableRecipeTree or;
    String recipeName;
    public BuildableRecipe(String recipeName) {
        this.recipeName = recipeName;
        and = new BuildableRecipeTree("AND");
        or = new BuildableRecipeTree("OR");
    }
    public boolean isSatisfied(Inventory inventory) {
        System.out.println("Checking and branch");
        and.mapForAll(node -> node.CountItem(inventory));
        System.out.println("Checking or branch");
        or.mapForAll(node -> node.CountItem(inventory));
        return and.isTrue() && or.isTrue();
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
        and.remove(inventory);
        or.remove(inventory);
        return this;
    }
    public String getRecipeName() {
        return recipeName;
    }
}
