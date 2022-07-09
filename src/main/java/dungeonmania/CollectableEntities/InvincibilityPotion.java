package dungeonmania.CollectableEntities;

public class InvincibilityPotion extends CollectableEntity{

    private int incincibility_potion_duration;

    public InvincibilityPotion(String id, String type, int x, int y, int incincibility_potion_duration) {
        super(id, type, x, y);
        this.incincibility_potion_duration = incincibility_potion_duration;
    }

    public void setIncincibility_potion_duration() {
        this.incincibility_potion_duration -= 1;
    }

    public int getIncincibility_potion_duration() {
        return incincibility_potion_duration;
    }
    
}
