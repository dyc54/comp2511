package dungeonmania.helpers;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.*;

import dungeonmania.Entity;
import dungeonmania.EntityController;
import dungeonmania.Observer;
import dungeonmania.Subject;
/**
 * Observer Pattern       
 * ! but not sure
 * Wo bu hui ...
 * @author Shilong
 */
public class DungeonMap implements Subject{
    private TreeMap<Location, HashSet<Entity>> map; 
    private HashMap<String, Location> IdCollection;
    public DungeonMap() {
        map = new TreeMap<>();
        IdCollection = new HashMap<>();
    }
    /**
     * Load entities
     * @param path
     * @throws IOException
     */
    public void loads(String path, Config config) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        JSONObject json =  new JSONObject(content);
        JSONArray entities = json.getJSONArray("entities");
        for (int i = 0; i < entities.length(); i++) {
            JSONObject entity = entities.getJSONObject(i);
            addEntity(EntityController.newEntity(entity, config));
        }
    }

    public void attach(Observer observer) {
        if (observer instanceof Entity) {
            addEntity((Entity) observer);
        }
        
    }

    /**
     * Add a Entity to Dungeon Map.
     * @param entity
     * @return
     */
    public DungeonMap addEntity(Entity entity) {
        // TODO: change to private if use observer pattern
        if (!IdCollection.containsKey(entity.getEntityId())) {
            IdCollection.put(entity.getEntityId(), entity.getLocation());
        }
        if (map.containsKey(entity.getLocation())) {
            map.get(entity.getLocation()).add(entity);
        } else {
            map.put(entity.getLocation(), new HashSet<>());
        }
        return this;
    }

    /**
     * Return whether Dungeon contains given entity
     * @param id entity id
     * @return
     */
    public boolean containsEntity(String id) {
        return IdCollection.containsKey(id);
    }

    /**
     * Return whether Dungeon contains given entity
     * @param entity
     * @return
     */
    public boolean containsEntity(Entity entity) {
        return containsEntity(entity.getEntityId());
    }

    /**
     * Return a Entity by given id.
     * @param id Entity id.
     * @return null if id does not exist
     */
    public Entity getEntity(String id) {
        if (!containsEntity(id)) {
            return null;
        }
        Location location = IdCollection.get(id);
        return map.get(location)
                    .stream()
                    .filter(entity ->  entity.getEntityId().equals(id))
                    .collect(Collectors.toList()).get(0);
    }

    /**
     * Return a Collection of Entities at given location.
     * @param location
     * @return empyty Collection will be return if there is no entity at given location
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
     * @param x
     * @param y
     * @return empyty Collection will be return if there is no entity at given location
     */
    public Collection<Entity> getEntities(int x, int y) {
        return getEntities(Location.AsLocation(x, y));
    }

    /**
     * Return a collection all entities in dungeon
     * @return
     */
    public Collection<Entity> getAllEntities() {
        Collection<Entity> entities = new LinkedList<>();
        for (HashSet<Entity> entities_set: map.values()) {
            entities.addAll(entities_set);
        }
        return entities;
    }
    public void detach(Observer observer) {
        if (observer instanceof Entity) {
            removeEntity(((Entity) observer).getEntityId());
        }
    }
    
    public void notifyObserver() {
        for (Observer obs: getAllEntities()) {
            obs.update(this);
        }
    }

    /**
     * Remove a given entity
     * @param id entity id
     * @return whether map successfully remove given entity
     */
    public boolean removeEntity(String id) {
        // TODO  change to private if use observer pattern
        if (!containsEntity(id)) {
            return false;
        }
        Location location = IdCollection.get(id);
        Collection<Entity> entities = getEntities(location);
        IdCollection.remove(id);
        return entities.removeIf(entity ->  entity.getEntityId().equals(id));
    }

    /**
     * Return a Collection of entities that located at top, bottom, left and right of given location
     * i.e.
     *          Top
     * Left     (Current)     Right
     *          Bottom
     * @param location
     * @apiNote Entities at current location will not be return.
     * @return
     */
    public Collection<Entity> getFourNearEntities(Location location) {
        Collection<Entity> entities = new LinkedList<>();
        location.getFourNearPosition().stream().forEach(position->{
            if (map.containsKey(position.apply(location))) {
                entities.addAll(map.get(position.apply(location)));
            }
        });
        return entities;
    } 

    /**
     * Return a Collection of entities that located at top, bottom, left and right of given location
     * i.e.
     *          Top
     * Left     (Current)     Right
     *          Bottom
     * @param x
     * @param y
     * @apiNote Entities at current location will not be return.
     * @return
     */
    public Collection<Entity> getFourNearEntities(int x, int y) {
        return getFourNearEntities(Location.AsLocation(x, y));
    } 

    /**
     * Return a Collection of nearby entities  of given location.
     * i.e. 
     * TopLeft      Top         TopRight
     * Left        (Current)    Right
     * BottomLeft   Bottom      BottomRight
     * @param location
     * @apiNote Entities at current location will not be return.
     * @return
     */
    public Collection<Entity> getEightNearEntities(Location location) {
        Collection<Entity> entities = new LinkedList<>();
        location.getEightNearPosition().stream().forEach(position->{
            if (map.containsKey(position.apply(location))) {
                entities.addAll(map.get(position.apply(location)));
            }
        });
        return entities;
    } 

    /**
     * Return a Collection of nearby entities  of given location.
     * i.e. 
     * TopLeft      Top         TopRight
     * Left        (Current)    Right
     * BottomLeft   Bottom      BottomRight
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
     * @param location
     * @param radius
     * @apiNote Entities at current location will be return.
     * @return
     */
    public Collection<Entity> getEntities(Location location, int radius) {
        Collection<Entity> entities = new LinkedList<>();
        for (Location target: map.keySet()) {
            if (location.distance(target) <= radius) {
                entities.addAll(map.get(target));
            }
        }
        return entities;
    }

    @Override
    public String toString() {
        String output = String.format("*********** %s ***********\n", "DungonMap");
        // for ()
        map.values()
            .stream()
            .forEach(hashset->{
                hashset.stream()
                        .forEach(entity ->{
                            output.concat(String.format("%s\n", entity.toString()));
                        });
            });
        return output;
    }
}
