package dungeonmania.CollectableEntities;

import dungeonmania.Entity;
import dungeonmania.Interactability;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.response.models.ItemResponse;

public abstract class CollectableEntity extends Entity implements Interactability {
    private boolean isCollected;
    public CollectableEntity(String type, int x, int y){
        super(type, x, y);
        isCollected = false;
    }
    public CollectableEntity(String type) {
        super(type);
        isCollected = false;

    }
    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
    

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        // TODO Auto-generated method stub
        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.pickup(this);
            map.removeEntity(getEntityId());
            if (DungeonMap.isaccessible(map, getLocation(), entity)) {
                entity.setLocation(getLocation());
            }
        }
        return false;
    }
    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap  map) {
        // do nothing by defalut 
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }
}
