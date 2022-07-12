package dungeonmania.Strategies;

import dungeonmania.helpers.DungeonMap;

public interface EnemyMovement extends MovementStrategy {
    public boolean movement(DungeonMap dungeonMap);
}
