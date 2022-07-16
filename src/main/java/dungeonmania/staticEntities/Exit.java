package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.helpers.Location;

public class Exit extends StaticEntity {
    private boolean playerExit;

    public Exit(String type, int x, int y) {
        super(type, x, y);
        this.playerExit = false;
    }

    public void setPlayerExit(boolean playerExit) {
        // To do something
        this.playerExit = playerExit;
    }

    public boolean getPlayerExit() {
        return this.playerExit;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return true;
    }

}
