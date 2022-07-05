package dungeonmania.CollectableEntities;

public class Key extends CollectableEntity {
    public Key(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
        setEntityId("key");
    }
}
