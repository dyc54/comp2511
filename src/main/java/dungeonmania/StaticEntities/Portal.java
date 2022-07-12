package dungeonmania.StaticEntities;

public class Portal extends StaticEntity {
    final String color;

    public Portal(String type, int x, int y, String color) {
        super(type, x, y);
        this.color = color;
    }
    /*
     * public String getColor() {
     * return this.color;
     * }
     */
}
