package dungeonmania.CollectableEntities.DurabilityEntities.BuildableEntities;

import dungeonmania.Inventories.Inventory;

public interface BuildableComponent {
    public String getItemType();
    public int getItemAmount();
    public boolean isSatisfied();
    public BuildableComponent CountItem(Inventory inventory);
    public BuildableComponent removeCountItem(Inventory inventory);
}
