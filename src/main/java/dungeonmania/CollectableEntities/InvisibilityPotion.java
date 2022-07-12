package dungeonmania.CollectableEntities;

public class InvisibilityPotion extends CollectableEntity{

    private int Invisibility_potion_duration;

    public InvisibilityPotion(String type, int x, int y, int Invisibility_potion_duration) {
        super(type, x, y);
        this.Invisibility_potion_duration = Invisibility_potion_duration;
    }
    
    public void setInvisibility_potion_duration() {
        Invisibility_potion_duration -= 1;
    }

    public int getInvisibility_potion_duration() {
        return Invisibility_potion_duration;
    }
}
