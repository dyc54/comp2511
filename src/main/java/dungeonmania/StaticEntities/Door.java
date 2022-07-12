package dungeonmania.StaticEntities;

public class Door extends StaticEntity {
    private final int key;
    public Door(String type, int x, int y, int key) {
        super(type, x, y);
        this.key = key;
    }
    public int getKey() {
        return key;
    }

}
