package dungeonmania.Strategies;

import dungeonmania.helpers.DungeonMap;

public interface EnemyMovementStrategy extends MovementStrategy {
    public void movement(DungeonMap dungeonMap);
}
