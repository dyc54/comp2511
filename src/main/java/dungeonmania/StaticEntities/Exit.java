package dungeonmania.StaticEntities;

import dungeonmania.Entity;

public class Exit extends Entity {
    public Exit(String type, int x, int y) {
        setType(type);
        setLocation(x, y);
        setEntityId("exit1");// 现在还不是唯一
    }

}
