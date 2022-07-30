package dungeonmania.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.*;

import dungeonmania.Accessibility;
import dungeonmania.Entity;
import dungeonmania.EntityFactory;
import dungeonmania.Interactability;
import dungeonmania.Player;
import dungeonmania.battle.Battle;
import dungeonmania.battle.Enemy;
import dungeonmania.logicEntities.LogicEntity;
import dungeonmania.movingEntities.MercenaryEnemy;
import dungeonmania.movingEntities.Spider;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.staticEntities.Exit;
import dungeonmania.staticEntities.Wall;
import dungeonmania.staticEntities.ZombieToastSpawner;
import dungeonmania.strategies.Movement;
import dungeonmania.timeTravel.TimeTravellingPortal;

/**
 * Save entities
 */
public class DungeonMap implements Iterable<Entity> {
    private TreeMap<Location, HashSet<Entity>> map;
    // private final HashMap<String, Location> IdCollection;
    private final IdCollection<Location> idCollection;
    private int EnemiesDestroiedCounter;
    Player player;
    private Timer timer;
    /**
     * Return whether two types are same type or same category.
     * 
     * @param EntityType
     * @param GivenType
     * @return
     */
    private static boolean isSameType(String EntityType, String givenType) {
        if (EntityType.equals(givenType)) {
            return true;
        }
        
        return false;
    }

    public DungeonMap() {
        map = new TreeMap<>();
        idCollection = new IdCollection<>();
        // IdCollection = new HashMap<>();
        EnemiesDestroiedCounter = 0;
        
    }
    public void setTimer(Timer time) {
        timer = time;
    }
    public Timer getTimer() {
        return timer;
    }
    /**
     * Load entities
     * 
     * @param path
     * @throws IOException
     */
    public DungeonMap loads(String path, Config config) throws IOException {
        
        String content = FileReader.LoadFile(path);
        JSONObject json = new JSONObject(content);
        JSONArray entities = json.getJSONArray("entities");
        boolean hasId = false;
        if (json.has("branch")) {
            hasId = json.getInt("branch") == 0;
        }
        for (int i = 0; i < entities.length(); i++) {
            JSONObject entity = entities.getJSONObject(i);
            addEntity(EntityFactory.newEntity(entity, config, this, hasId));
        }
        toString();
        return this;
    }
    public DungeonMap loads(RandomMapGenerator map, Config config) {
        Iterator<Location> wallLocation = map.iterator();
        while (wallLocation.hasNext()) {
            addEntity(new Wall("wall", wallLocation.next()));
        }
        addEntity(new Player("player", map.getStartLocation().getX(), map.getStartLocation().getY(), config.player_attack, config.player_health, this));
        addEntity(new Exit("exit", map.getEndLocation().getX(), map.getEndLocation().getY()));
        return this;
    }
    /**
     * Add a Entity to Dungeon Map.
     * 
     * @param entity
     * @return
     */
    public DungeonMap addEntity(Entity entity) {
        if (!idCollection.hasId(entity.getEntityId())) {
            idCollection.put(entity.getEntityId(), entity.getLocation().clone());   
        }
        if (entity instanceof Player) {
            // System.out.println("Gett player");
            this.player = (Player) entity;
            // System.out.println(this.player.toString());
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
        return idCollection.hasId(id);
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
    public Player getPlayer() {
        return player;
    }
    /**
     * Return a Entity by given id.
     * 
     * @param id Entity id.
     * @return null if id does not exist
     */
    public Entity getEntity(String id) {
        if (!containsEntity(id)) {
            return null;
        }
        Location location = idCollection.get(id);
        // map.keySet().stream().forEach(entity -> System.out.println(entity + ":"+ location.toString()+entity.equals(location.toString())));
        return map.get(location)
                .stream()
                .filter(entity -> entity.getEntityId().equals(id))
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
            entities.addAll(new LinkedList<>(entities_set));
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
        // if (type.equals("player")) {
        //     return Arrays.asList(player);
        // }
        Collection<Entity> entities = new LinkedList<>();
        getAllEntities().stream().forEach(entity -> {
            if (DungeonMap.isSameType(entity.getType(), type)) {
                entities.add(entity);
                System.out.println(String.format("Get Entities by given type %s", entity.toString()));
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
        if (!containsEntity(id)) {
            return;
        }
        // Location location = idCollection.get(id);
        // Collection<Entity> entities = getEntities(location);
        Entity temp = getEntity(id);
        removeEntity(temp);
        // if (temp instanceof Enemy) {
        //     EnemiesDestroiedCounter += 1;

        // }
        
        // System.out.println(String.format("Entity %s %s has removed from Map", temp.getType(), temp.getEntityId()));
        // entities.remove(temp);
        // idCollection.remove(id);
    }
    public void removeEntity(Entity entity) {
        if (!containsEntity(entity.getEntityId())) {
            return;
        }
        Location location = idCollection.get(entity.getEntityId());
        
        Collection<Entity> entities = getEntities(location);
        // Entity temp = getEntity(id);
        if (entity instanceof Enemy) {
            EnemiesDestroiedCounter += 1;

        }
        
        System.out.println(String.format("Entity %s %s has removed from Map", entity.getType(), entity.getEntityId()));
        idCollection.remove(entity.getEntityId());
        entities.remove(entity);
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
     * Move all entities with their movement strategy
     */
    public void moveAllEntities() {
        Collection<Entity> entities = getAllEntities();
        entities.stream().forEach(entity -> {
            if (entity instanceof Movement) {
                Movement movingEntity = (Movement) entity;
                movingEntity.movement(this);
            }
            if (entity instanceof ZombieToastSpawner) {
                ((ZombieToastSpawner) entity).ZombieToastSpwanCheck();
            }
        });
    }


    public TreeMap<Location, HashSet<Entity>> gMap(){
        return map;
    }

    /**
     * Update Entity position
     * 
     * @param entity entity that has already move
     */
    public void UpdateEntity(Entity entity) {
        if (!entity.getLocation().equals(idCollection.get(entity.getEntityId()))) {
            int temp = EnemiesDestroiedCounter;
            System.out.println(String.format("Update %s", entity.toString()));
            removeEntity(entity);
            addEntity(entity);
            EnemiesDestroiedCounter = temp;
        }
        // if (entity instanceof LogicEntity ) {
        //     UpdateLogicEntity((LogicEntity) entity);
        // }

    }
    private void UpdateLogicEntity(LogicEntity entity) {
        entity.init(this);
    }
    public int getDestoriedCounter() {
        System.out.println(String.format("Counter = ", EnemiesDestroiedCounter));
        return EnemiesDestroiedCounter;
    }
    public void UpdateAllLogicalEntities() {
        getAllEntities().stream().forEach(entity -> {
            if (entity instanceof LogicEntity ) {
                UpdateLogicEntity((LogicEntity) entity);
            }
        });
    }
    public void UpdateAllEntities() {
        getAllEntities().stream().forEach(entity -> UpdateEntity(entity));
    }
    public DungeonMap interactAll() {
        // List<Interactability> list = new ArrayList<>();
        // System.out.println("ITERACT ALL");
        // for (Iterator<Entity> ptr = getEntities(player.getLocation()).iterator(); ptr.hasNext();) {
        //     Entity entity = ptr.next();
        //     if (entity instanceof Interactability) {
        //         list.add((Interactability) entity);
        //     }
        // }
        // list.stream().forEach(en -> en.interact(player, this));
        this.interactAll(player);
        return this;
    }
    public DungeonMap interactAll(Entity entity) {
        List<Interactability> list = new ArrayList<>();
        System.out.println("ITERACT ALL");
        for (Iterator<Entity> ptr = getEntities(entity.getLocation()).iterator(); ptr.hasNext();) {
            Entity temp = ptr.next();
            if (temp instanceof Interactability) {
                list.add((Interactability) temp);
            }
        }
        list.stream().forEach(en -> en.interact(entity, this));
        return this;
    }
    public DungeonMap interactAll(Location location, Entity entity) {
        List<Interactability> list = new ArrayList<>();
        System.out.println("ITERACT ALL");
        for (Iterator<Entity> ptr = getEntities(location).iterator(); ptr.hasNext();) {
            Entity temp = ptr.next();
            if (temp instanceof Interactability) {
                list.add((Interactability) temp);
            }
        }
        list.stream().forEach(en -> en.interact(entity, this));
        return this;
    }
    public DungeonMap battleAll(List<BattleResponse> battles, Player player) {
        String effect = "";
        if (player.hasEffect()) {
            effect = player.getCurrentEffect().applyEffect();
        }
        System.out.println(player.getLocation());
        this.getEntities(player.getLocation()).stream().forEach(entity -> System.out.println(entity.toString()));

        System.out.println("PLAYER CHECK BATTLE");
        // if (this.getEntities(player.getLocation()).size() > 1) {
            List<String> removed = new ArrayList<>();
            List<Movement> movements = new ArrayList<>();
            System.out.println(player.getLocation().toString());
            for (Entity entity: this.getEntities(player.getLocation())) {
                System.out.println(entity.toString() + "BATTLE CHECK");
                if (entity instanceof Enemy && player.canBattle(entity)) {
                    Battle battle = new Battle();
                    List<String> losers = battle.setBattle(player, (Enemy) entity).startBattle();
                    losers.stream().forEach(loser -> removed.add(loser));
                    System.out.println(String.format("loser :"));
                    removed.stream().forEach(loser -> System.out.println(loser));
                    if (player.hasEffect() && player.getCurrentEffect().applyEffect().equals("Invincibility")) {
                        if (!(entity instanceof Spider)) {
                            if (entity instanceof MercenaryEnemy) {
                                entity.setLocation(player.getPreviousLocation());
                            } else {
                                movements.add((Movement) entity);
                            }
                        }
                    }
                    battles.add(battle.toResponse());
                    player.cleardisusableItem();
                }
            }
            removed.stream().forEach(id -> this.removeEntity(id));
            movements.stream().forEach(ent -> ent.movement(this));
            
        // }
        return this;
    }
    public void setPlayer(Player player) {
        removeEntity(this.player.getEntityId());
        addEntity(player);
        // this.player = player;
    }

    public static boolean isaccessible(DungeonMap map, Location location, Entity entity) {
        List<Entity> list = DungeonMap.blockedEntities(map, location, entity);
        System.out.println(String.format("For entity %s, unaccessable entities at %s are:", entity.toString(), location.toString()));
        list.stream().forEach(e -> System.out.println(e.toString()));
        return DungeonMap.blockedEntities(map, location, entity).size() == 0;
    }
    public static List<Entity> blockedEntities(DungeonMap map, Location location, Entity entity) {
        return map.getEntities(location).stream()
            .filter(element ->  location.equals(element.getLocation()) && element instanceof Accessibility &&!((Accessibility) element).isAccessible(entity))
            .collect(Collectors.toList());
        
    }
    public static boolean hasInteractableEntity(DungeonMap map, Location location, Entity entity) {
        
        List<Entity> list =  DungeonMap.InteracyableEntities(map, location, entity);
        System.out.println(String.format("For entity %s, Interacyable  entities at %s are:", entity.toString(), location.toString()));
        list.stream().forEach(e -> System.out.println(e.toString()));
        return DungeonMap.InteracyableEntities(map, location, entity).size() != 0;
    }

    public static List<Entity> InteracyableEntities(DungeonMap map, Location location, Entity entity) {
        return map.getEntities(location).stream()
            .filter(element ->  location.equals(element.getLocation()) && element instanceof Interactability && ((Interactability) element).hasSideEffect(entity, map))
            .collect(Collectors.toList());
    }
    public void print() {
        System.out.println("Map:");
        getAllEntities().stream().forEach(entity -> System.out.println(entity));
    }
    @Override
    public Iterator<Entity> iterator() {
        return getAllEntities().iterator();
    }
    public boolean isTimeTravelPortal(Location location) {
        return getEntities(location).stream().anyMatch(entity -> entity instanceof TimeTravellingPortal);
    }
    public Stream<Entity> stream() {
        return getAllEntities().stream();
    }
}
