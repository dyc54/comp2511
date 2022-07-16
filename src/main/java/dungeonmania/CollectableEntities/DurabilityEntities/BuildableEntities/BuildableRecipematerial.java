package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import dungeonmania.Inventories.Inventory;

public class BuildableRecipematerial implements BuildableComponent{
    private String materialType;
    private int materialAmount;
    private int currentAmount;
    public BuildableRecipematerial(String materialType, int materialAmount) {
        this.materialType = materialType;
        this.materialAmount = materialAmount;
        currentAmount = 0;
    }
    @Override
    public String getItemType() {
        return getType();
    }
    @Override
    public int getItemAmount() {
        return getAmount();
    }
    @Override
    public String toString() {
        return String.format("Item %s need %d/%d", materialType, currentAmount, materialAmount);
    }
    public String getType() {
        return materialType;
    }
    public int getAmount() {
        return materialAmount;
    }
    @Override
    public boolean isSatisfied() {
        System.out.println(toString());
        return currentAmount >= materialAmount;
    }
    @Override
    public BuildableComponent CountItem(Inventory inventory) {
        System.out.println(String.format("Material loading for %s", materialType));
        setCurrentAmount(inventory.countItem(materialType));
        return this;
    }
    @Override
    public BuildableComponent removeCountItem(Inventory inventory) {
        inventory.removeFromInventoryList(materialType, materialAmount);
        return this;
    }

    public void setCurrentAmount(int amount) {
        currentAmount = amount > 0 ? amount : 0;
    }
    public void remove(Inventory inventory) {
        inventory.removeFromInventoryList(materialType, materialAmount);
        setCurrentAmount(0);
    }

}
