package dungeonmania.CollectableEntities;

public class Key extends CollectableEntity {
    private final int key;
    public Key(String type, int x, int y, int key) {
        super(type, x, y);
        this.key = key;
    }
    public int getKey() {
        return key;
    }
}
