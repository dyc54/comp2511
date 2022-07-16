package dungeonmania.collectableEntities.durabilityEntities;

public class InvincibilityPotion extends PotionEntity{

    public InvincibilityPotion(String type, int durability, int x, int y) {
        
        super(type, durability, x, y, "Invincibility");
        // System.out.println(x*100 + y);
        // System.out.println(getLocation().toString());
    }
    @Override
    public String applyEffect() {
        return "Invincibility";
    }
}
