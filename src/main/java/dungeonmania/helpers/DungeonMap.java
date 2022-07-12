package dungeonmania.helpers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.*;

import dungeonmania.Entity;
import dungeonmania.EntityFactory;
import dungeonmania.Player;
import dungeonmania.MovingEntities.MovingEntity;
import dungeonmania.Strategies.MovementStrategies.*;

/**
 * Observer Pattern
 * ! but not sure
 * Wo bu hui ...
 * 
 * @author Shilong
 */
public class DungeonMap {
    private TreeMap<Location, HashSet<Entity>> map;
    private HashMap<String, Location> IdCollection;
    private int EnemiesDestroiedCounter;

    /**
     * Return whether two types are same type or same category.
     * 
     * @param EntityType
     * @param GivenType
     * @return
     */
    private static <X, Y> boolean isSameType(String EntityType, String givenType) {
        if (EntityType.equals(givenType)) {
            return true;
        }
        if (givenType.toLowerCase().equals(new String("enemies"))) {
            return Arrays.asList("Spider", "Zombie Toast", "Zombie", "Unbribed Mercenary").contains(EntityType);
        }
        
        return false;
    }

    public DungeonMap() {
        map = new TreeMap<>();
        IdCollection = new HashMap<>();
        EnemiesDestroiedCounter = 0;
        
    }

    /**
     * Load entities
     * 
     * @param path
     * @throws IOException
     */
    public void loads(String path, Config config) throws IOException {
        
        String content = FileReader.LoadFile(path);
        JSONObject json = new JSONObject(content);
        JSONArray entities = json.getJSONArray("entities");
        for (int i = 0; i < entities.length(); i++) {
            JSONObject entity = entities.getJSONObject(i);
            addEntity(EntityFactory.newEntity(entity, config, this));
        }
    }

    /**
     * Add a Entity to Dungeon Map.
     * 
     * @param entity
     * @return
     */
    public DungeonMap addEntity(Entity entity) {
        // TODO: change to private if use observer pattern
        if (!IdCollection.containsKey(entity.getEntityId())) {
            IdCollection.put(entity.getEntityId(), entity.getLocation().clone());
        }
        if (map.containsKey(entity.getLocation())) {
            map.get(entity.getLocation()).add(entity);
        } else {
            HashSet<Entity> sites = new HashSet<>();
            sites.add(entity);
            map.put(entity.getLocation().clone(), sites);
        }
        
        return this;
    }

    /**
     * Return whether Dungeon contains given entity
     * 
     * @param id entity id
     * @return
     */
    public boolean containsEntity(String id) {
        return IdCollection.containsKey(id);
    }

    /**
     * Return whether Dungeon contains given entity
     * 
     * @param entity
     * @return
     */
    public boolean containsEntity(Entity entity) {
        return containsEntity(entity.getEntityId());
    }

    /**
     * Return a Entity by given id.
     * 
     * @param id Entity id.
     * @return null if id does not exist
     */
    public Entity getEntity(String id) {
        // !
        if (!containsEntity(id)) {
            return null;
        }
        Location location = IdCollection.get(id);
        map.keySet().stream().forEach(entity -> System.out.println(entity + ":"+ location.toString()+entity.equals(location.toString())));
        return map.get(location)
                .stream()
                .filter(entity -> {System.out.println(entity.toString()); 
                    return entity.getEntityId().equals(id);})
                .collect(Collectors.toList()).get(0);
        // return null;
    }

    /**
     * Return a Collection of Entities at given location.
     * 
     * @param location
     * @return empyty Collection will be return if there is no entity at given
     *         location
     */
    public Collection<Entity> getEntities(Location location) {
        if (map.containsKey(location)) {
            return map.get(location);
        } else {
            return new LinkedList<>();
        }
    }

    /**
     * Return a Collection of Entities at given location.
     * 
     * @param x
     * @param y
     * @return empyty Collection will be return if there is no entity at given
     *         location
     */
    public Collection<Entity> getEntities(int x, int y) {
        return getEntities(Location.AsLocation(x, y));
    }

    /**
     * Return a collection all entities in dungeon
     * 
     * @return
     */
    public Collection<Entity> getAllEntities() {
        Collection<Entity> entities = new LinkedList<>();
        for (HashSet<Entity> entities_set : map.values()) {
            entities.addAll(entities_set);
        }
        return entities;
    }

    /**
     * Return a collection of entitis by given a entities type or category
     * 
     * @param type e.g. "door" or "enemies"
     * @return
     */
    public Collection<Entity> getEntities(String type) {
        Collection<Entity> entities = new LinkedList<>();
        getAllEntities().stream().forEach(entity -> {
            if (DungeonMap.isSameType(entity.getType(), type)) {
                entities.add(entity);
            }
        });
        return entities;
    }

    /**
     * Remove a given entity
     * 
     * @param id entity id
     * @return whether map successfully remove given entity
     */
    public void removeEntity(String id) {
        // TODO change to private if use observer pattern
        if (!containsEntity(id)) {
            return;
        }
        Location location = IdCollection.get(id);
        Collection<Entity> entities = getEntities(location);
        Entity temp = getEntity(id);
        if (isSameType(temp.getEntityId(), "enemies")) {
            EnemiesDestroiedCounter -= 1;
        }
        entities.remove(temp);
        IdCollection.remove(id);
    }

    /**
     * Return a Collection of entities that located at top, bottom, left and right
     * of given location
     * i.e.
     * Top
     * Left (Current) Right
     * Bottom
     * 
     * @param location
     * @apiNote Entities at current location will not be return.
     * @return
     */
    public Collection<Entity> getFourNearEntities(Location location) {
        Collection<Entity> entities = new LinkedList<>();
        location.getFourNearPosition().stream().forEach(position -> {
            if (map.containsKey(position.apply(location))) {
                entities.addAll(map.get(position.apply(location)));
            }
        });
        return entities;
    }

    /**
     * Return a Collection of entities that located at top, bottom, left and right
     * of given location
     * i.e.
     * Top
     * Left (Current) Right
     * Bottom
     * 
     * @param x
     * @param y
     * @apiNote Entities at current location will not be return.
     * @return
     */
    public Collection<Entity> getFourNearEntities(int x, int y) {
        return getFourNearEntities(Location.AsLocation(x, y));
    }

    /**
     * Return a Collection of nearby entities of given location.
     * i.e.
     * TopLeft Top TopRight
     * Left (Current) Right
     * BottomLeft Bottom BottomRight
     * 
     * @param location
     * @apiNote Entities at current location will not be return.
     * @return
     */
    public Collection<Entity> getEightNearEntities(Location location) {
        Collection<Entity> entities = new LinkedList<>();
        location.getEightNearPosition().stream().forEach(position -> {
            if (map.containsKey(position.apply(location))) {
                entities.addAll(map.get(position.apply(location)));
            }
        });
        return entities;
    }

    /**
     * Return a Collection of nearby entities of given location.
     * i.e.
     * TopLeft Top TopRight
     * Left (Current) Right
     * BottomLeft Bottom BottomRight
     * 
     * @param x
     * @param y
     * @apiNote Entities at current location will not be return.
     * @return
     */
    public Collection<Entity> getEightNearEntities(int x, int y) {
        return getEightNearEntities(Location.AsLocation(x, y));
    }

    /**
     * Return a Collection of near entities
     * 
     * @param location
     * @param radius
     * @apiNote Entities at current location will be return.
     * @return
     */
    public Collection<Entity> getEntities(Location location, int radius) {
        Collection<Entity> entities = new LinkedList<>();
        for (Location target : map.keySet()) {
            if (location.distance(target) <= radius) {
                entities.addAll(map.get(target));
            }
        }
        return entities;
    }

    /**
     * map a function for all entities
     * 
     * @param <X>      Returnd type for function
     * @param function
     */
    public <X> void mapToAllEntities(Function<Entity, X> function) {
        Collection<Entity> entities = getAllEntities();
        entities.stream().map(function);
    }

    /**
     * Move all entities with their movement strategy
     */
    public void moveAllEntities() {
        Collection<Entity> entities = getAllEntities();
        entities.stream().forEach(entity -> {
            if (entity instanceof MovementStrategy) {
                // TODO: do something
                MovementStrategy a;
            }
        });
    }

    /**
     * Update Entity position
     * 
     * @param entity entity that has already move
     */
    public void UpdateEntity(Entity entity) {
        removeEntity(entity.getEntityId());
        addEntity(entity);
        System.out.println("---" + entity.getType());
        System.out.println("map" + IdCollection.get(entity.getEntityId()).toString());
    }

    /**
     * Move entity with given movement strategy
     * 
     * @param entity
     * @param movement
     * @param location
     */
    public void moveEntity(Entity entity, MovementStrategy movement) {
        // TODO:

    }

    /**
     * Move entity with its movement strategy
     * 
     * @param entity
     * @param movement
     * @param location
     */
    public void moveEntity(Entity entity) {
        // TODO:

    }

    public int getDestoriedCounter() {
        return EnemiesDestroiedCounter;
    }
    @Override
    public String toString() {
        String output = String.format("*********** %s ***********\n", "DungonMap");
        map.values()
                .stream()
                .forEach(hashset -> {
                    hashset.stream()
                            .forEach(entity -> {
                                output.concat(String.format("%s\n", entity.toString()));
                            });
                });
        return output;
    }
}
