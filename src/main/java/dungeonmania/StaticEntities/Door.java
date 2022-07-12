package dungeonmania.StaticEntities;

public class Door extends StaticEntity {
    private final String key;
    public Door(String type, int x, int y, String key) {
        super(type, x, y);
        this.key = key;
    }
    public String getKey() {
        return key;
    }

}
