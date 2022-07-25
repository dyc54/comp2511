package dungeonmania.CollectableEntities;

import dungeonmania.Accessibility;
import dungeonmania.Entity;
import dungeonmania.Interactability;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.response.models.ItemResponse;

public abstract class CollectableEntity extends Entity implements Interactability, Accessibility{
    public CollectableEntity(String type, int x, int y){
        super(type, x, y);
    }
    public CollectableEntity(String type) {
        super(type);

    }
    public ItemResponse getItemResponse(){
        return new ItemResponse(getEntityId(),getType());
    }
    

    @Override
    public boolean interact(Entity entity, DungeonMap map) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.pickup(this)) {
                map.removeEntity(getEntityId());
                if (DungeonMap.isaccessible(map, getLocation(), entity)) {
                    entity.setLocation(getLocation());
                }
            }
        }
        return false;
    }
    @Override
    public boolean hasSideEffect(Entity entity, DungeonMap  map) {
        return DungeonMap.isaccessible(map, getLocation(), entity);
    }

    @Override
    public boolean isAccessible(Entity entity) {
        return true;
    }
}
