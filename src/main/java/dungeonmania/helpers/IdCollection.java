package dungeonmania.helpers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
/**
 * Propose
 * to quicker access datas at inventory and dungeon map.
 * similar code at inventory and map.
 */
public class IdCollection <T> {
    HashMap<String, T> Ids = new HashMap<>();
    
    public void put(String id, T t) {
        Ids.put(id, t);
    }
    public boolean remove(String id) {
        return Ids.remove(id) != null;
    }

    public boolean hasId(String id) {
        return Ids.containsKey(id);
    }

    public T get(String id) {
        return Ids.get(id);
    }

    public void replace(String id, T t) {
        Ids.replace(id, t);
    }

    public Collection<T> Values() {
        return Ids.values();
    }

    public Set<String> Keys() {
        return Ids.keySet();
    }
}
