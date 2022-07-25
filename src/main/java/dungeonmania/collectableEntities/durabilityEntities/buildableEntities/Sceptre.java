package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.Useable;
import dungeonmania.helpers.DungeonMap;

public class Sceptre extends Entity implements Useable{

    private int Duration;
    public Sceptre(String type, int Duration, String id) {
        super(type);
        this.Duration = Duration;
    }
    @Override
    public void use(DungeonMap map, Player player) {
        
    }


    
}
