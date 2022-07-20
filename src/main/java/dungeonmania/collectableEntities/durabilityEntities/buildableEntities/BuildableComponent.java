package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.inventories.Inventory;

public interface BuildableComponent {
    public String getItemType();
    public int getItemAmount();
    public boolean isSatisfied();
    public BuildableComponent CountItem(Inventory inventory);
    public BuildableComponent removeCountItem(Inventory inventory);
}