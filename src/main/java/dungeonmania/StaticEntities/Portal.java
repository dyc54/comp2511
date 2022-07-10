package dungeonmania.StaticEntities;

public class Portal extends StaticEntity {
    String colour;

    public Portal(String type, int x, int y, String colour) {
        setType(type);
        setLocation(x, y);
        this.colour = colour;
    }

    public String getColour() {
        return this.colour;
    }

}
