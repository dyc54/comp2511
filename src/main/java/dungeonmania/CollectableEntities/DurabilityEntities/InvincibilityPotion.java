package dungeonmania.CollectableEntities.DurabilityEntities;

public class InvincibilityPotion extends PotionEntity{

    public InvincibilityPotion(String id, String type, int durability, int x, int y) {
        super(id, type, durability, x, y);
        setEffect("Invincibility");
    }
    
}
