package dungeonmania.CollectableEntities;

public class Bomb extends CollectableEntity{

    private int bomb_radius;

    public Bomb(String type, int x, int y, int bomb_radius) {
        super(type, x, y);
        this.bomb_radius = bomb_radius;
    }

    public int getBomb_radius() {
        return bomb_radius;
    }
    
}
