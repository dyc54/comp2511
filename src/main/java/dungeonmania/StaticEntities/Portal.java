package dungeonmania.StaticEntities;

public class Portal extends StaticEntity {
    String color;

    public Portal(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
        this.color = color;
    }
    /*
     * public String getColor() {
     * return this.color;
     * }
     */
}
