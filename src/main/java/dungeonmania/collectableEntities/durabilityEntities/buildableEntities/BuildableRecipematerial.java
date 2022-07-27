package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.inventories.Inventory;
import dungeonmania.inventories.InventoryViewer;

public class BuildableRecipematerial implements BuildableComponent{
    private String materialType;
    private BuildableComponent replace;
    private int materialAmount;
    private int currentAmount;
    private boolean consumed;
    public BuildableRecipematerial(String materialType, int materialAmount) {
        this.materialType = materialType;
        this.materialAmount = materialAmount;
        currentAmount = 0;
        consumed = true;
    }
    public BuildableRecipematerial(String materialType, int materialAmount, boolean consumed) {
        this(materialType, materialAmount);
        this.consumed = consumed;
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
        return (hasReplacement() && replace.isSatisfied()) || currentAmount >= materialAmount;
    }
    @Override
    public BuildableComponent CountItem(InventoryViewer inventory) {
        System.out.println(String.format("Material loading for %s", materialType));
        setCurrentAmount(inventory.countItem(materialType));
        inventory.removeFromInventoryList(materialType, materialAmount);
        if (!isSatisfied() && hasReplacement()) {
            replace.CountItem(inventory);
        }
        return this;
    }
    @Override
    public BuildableComponent removeCountItem(Inventory inventory) {
        if (hasReplacement() && replace.isSatisfied()) {
            replace.removeCountItem(inventory);
        } else {
            remove(inventory);
            // inventory.removeFromInventoryList(materialType, materialAmount);
        }
        return this;
    }

    public void setCurrentAmount(int amount) {
        currentAmount = amount > 0 ? amount : 0;
    }
    public void remove(Inventory inventory) {
        if (consumed) {
            inventory.removeFromInventoryList(materialType, materialAmount, null);
        }
        setCurrentAmount(0);
    }
    @Override
    public BuildableComponent setReplacement(BuildableComponent component) {
        replace = component;
        return null;
    }
    @Override
    public boolean hasReplacement() {
        // TODO Auto-generated method stub
        return replace != null;
    }

}
