package dungeonmania.Strategies;

import dungeonmania.util.Position;

public interface PlayerMovementStrategy extends Movement {
    public void movement(Position position);
}
