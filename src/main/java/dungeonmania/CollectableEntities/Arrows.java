package dungeonmania.CollectableEntities;

public class Arrows extends CollectableEntity{
    private int Bow_durability;

    public Arrows(String type, int x, int y, int Bow_durability) {
        super(type, x, y);
        this.Bow_durability = Bow_durability;
    }

    public int getBow_durability() {
        return Bow_durability;
    }
    
}
