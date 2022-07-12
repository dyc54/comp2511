package dungeonmania.StaticEntities;

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

}
