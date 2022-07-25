package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import com.google.gson.internal.bind.MapTypeAdapterFactory;

import dungeonmania.inventories.Inventory;

public class BuildableRecipeReplacement implements BuildableComponent{
    private BuildableComponent component;
    private boolean needConsumed;
    public BuildableRecipeReplacement(BuildableComponent component, boolean needConsumed) {
        this.component = component;
        this.needConsumed = needConsumed;
    }
    public BuildableRecipeReplacement(String type, int amount, boolean needConsumed) {
        this.component = new BuildableRecipematerial(type, amount);
        this.needConsumed = needConsumed;
    }
    @Override
    public String getItemType() {
        return component.getItemType();
    }

    @Override
    public int getItemAmount() {
        return component.getItemAmount();
    }

    @Override
    public boolean isSatisfied() {
        return component.isSatisfied();
    }

    @Override
    public BuildableComponent CountItem(Inventory inventory) {
        return component.CountItem(inventory);
    }

    @Override
    public BuildableComponent removeCountItem(Inventory inventory) {
        if (needConsumed) {
            component.removeCountItem(inventory);
        } 
        return this;
    }
    @Override
    public BuildableComponent setReplacement(BuildableComponent component) {
        // TODO Auto-generated method stub
        return this;
    }
    @Override
    public boolean hasReplacement() {
        // TODO Auto-generated method stub
        return false;
    }
    
}
