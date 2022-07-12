package dungeonmania.CollectableEntities;

public class Key extends CollectableEntity {
    private final String key;
    public Key(String type, int x, int y, String key) {
        super(type, x, y);
        this.key = key;
    }
    public String getKey() {
        return key;
    }
}
