package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.inventories.Inventory;
import dungeonmania.inventories.InventoryViewer;

public interface BuildableComponent {
    public String getItemType();
    public int getItemAmount();
    public boolean isSatisfied();
    public BuildableComponent CountItem(InventoryViewer inventory);
    public BuildableComponent removeCountItem(Inventory inventory);
    public BuildableComponent setReplacement(BuildableComponent component);
    public boolean hasReplacement();
}
