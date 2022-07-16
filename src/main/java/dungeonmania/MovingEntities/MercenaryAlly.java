package dungeonmania.MovingEntities;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.MovementStrategies.FollowingMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class MercenaryAlly extends MovingEntity implements EnemyMovement{
    public MercenaryAlly(String type, Location location, double ally_attack, double ally_defence) {
        super(type, location, ally_defence, new BaseAttackStrategy(ally_attack), new FollowingMovement());
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        MovementStrategy strategy = super.getMove();
        Location playerPreLocation = new Location();
        Collection<Entity> player = dungeonMap.getEntities("player");
        for (Entity entity: player) {
            Player p = (Player) entity;
            playerPreLocation = p.getPreviousLocation();
        }
        Location next = strategy.nextLocation(playerPreLocation);
        setLocation(next);
        dungeonMap.UpdateEntity(this);
        return true;
    }
    
}
