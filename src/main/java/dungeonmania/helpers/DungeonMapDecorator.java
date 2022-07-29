package dungeonmania.helpers;

public abstract class DungeonMapDecorator {
    protected DungeonMap map;
    public DungeonMapDecorator(DungeonMap map) {
        this.map = map;
    }
}
