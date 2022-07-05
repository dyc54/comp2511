package dungeonmania.StaticEntities;

public class Wall extends StaticEntity {
    public Wall(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
    }
}
