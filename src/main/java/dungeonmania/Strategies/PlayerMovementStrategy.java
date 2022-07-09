package dungeonmania.Strategies;

import dungeonmania.util.Position;

public interface PlayerMovementStrategy extends MovementStrategy {
    public void movement(Position position);
}
