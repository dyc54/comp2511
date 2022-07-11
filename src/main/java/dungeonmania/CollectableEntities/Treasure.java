package dungeonmania.CollectableEntities;

public class Treasure extends CollectableEntity{

    public Treasure(String id, String type, int x, int y) {
        super(id, type);
        setLocation(x, y);
    }
    
}
