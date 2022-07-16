package dungeonmania.strategies;

import dungeonmania.helpers.DungeonMap;

public interface EnemyMovement extends Movement  {
    public boolean movement(DungeonMap dungeonMap);
}
