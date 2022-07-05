package dungeonmania.StaticEntities;

import dungeonmania.helpers.Location;

public class Exit extends StaticEntity {
    public Exit(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
    }

    /**
     * Return true if player's location is equal to exit's location
     * 
     * @param Location
     * @return boolean
     */
    public boolean playerIsOnExit(Location location) {
        // To do something
        return false;
    }

}
