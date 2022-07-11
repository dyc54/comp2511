package dungeonmania.CollectableEntities;

public class Key extends CollectableEntity {

    public Key(String id, String type, int x, int y) {
        super(id, type);
        setLocation(x, y);
    }
    
}
