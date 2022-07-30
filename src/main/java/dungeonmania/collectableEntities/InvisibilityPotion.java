package dungeonmania.collectableEntities;

public class InvisibilityPotion extends PotionEntity{

    public InvisibilityPotion(String type, int durability, int x, int y) {
        super(type, durability, x, y, "Invisibility");
    }
    @Override
    public String applyEffect() {
        return "Invisibility";
    }

}
