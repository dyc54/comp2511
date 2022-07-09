package dungeonmania.CollectableEntities;

public class Treasure extends CollectableEntity {
    public Treasure(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
        setEntityId("Treasure");
    }
}
