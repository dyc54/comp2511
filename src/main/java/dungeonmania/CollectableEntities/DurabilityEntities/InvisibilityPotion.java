package dungeonmania.CollectableEntities.DurabilityEntities;

public class InvisibilityPotion extends PotionEntity{

    public InvisibilityPotion(String id, String type, int durability, int x, int y) {
        super(id, type, durability, x, y);
        setEffect("Invisibility");
    }

}
