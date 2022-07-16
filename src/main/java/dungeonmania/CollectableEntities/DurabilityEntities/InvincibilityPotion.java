package dungeonmania.CollectableEntities.DurabilityEntities;

public class InvincibilityPotion extends PotionEntity{

    public InvincibilityPotion(String type, int durability, int x, int y) {
        
        super(type, durability, x, y, "Invincibility");
    }
    @Override
    public String applyEffect() {
        return "Invincibility";
    }
}
