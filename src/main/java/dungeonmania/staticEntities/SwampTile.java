package dungeonmania.staticEntities;

import dungeonmania.Entity;

public class SwampTile extends StaticEntity{
    private int multiplyingFactor;

    public SwampTile(String type, int x, int y,int multiplyingFactor) {
        super(type, x, y);
        this.multiplyingFactor = multiplyingFactor;
    }

    public int getMultiplyingFactor() {
        return multiplyingFactor;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }
    
}
