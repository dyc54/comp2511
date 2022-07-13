package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Mercenary extends MovingEntity implements EnemyMovement, Interact {
    // Location location;
    double mercenary_attack;
    double mercenary_health;
    int bribe_amount;
    int bribe_radius;
    int ally_attack;
    int ally_defence;
    public Mercenary(String type, Location location, double mercenary_attack, double mercenary_health, int bribe_amount, int bribe_radius, int ally_attack, int ally_defence) {
        super(type, location, mercenary_health, new BaseAttackStrategy(mercenary_attack), new ChaseMovement(location));
        this.mercenary_attack = mercenary_attack;
        this.mercenary_health = mercenary_health;
        this.bribe_amount = bribe_amount;
        this.bribe_radius = bribe_radius;
        this.ally_attack = ally_attack;
        this.ally_defence = ally_defence;
    }

    private boolean checkMovement(DungeonMap dungeonMap, Location next) {
        return dungeonMap.getEntities(next).stream().anyMatch(entity -> entity.getType().equals("wall") || entity.getType().equals("boulder") || entity.getType().equals("door"));
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        MovementStrategy strategy = super.getMove();
        Location playerLocation = new Location();
        Collection<Entity> player = dungeonMap.getEntities("player");
        for (Entity entity: player) {
            Player p = (Player) entity;
            playerLocation = p.getLocation();
        }
        Location next = strategy.nextLocation(playerLocation);
        if (!checkMovement(dungeonMap, next)) {
            setLocation(next);
        } else {
            next = strategy.moveWithWall(next, dungeonMap);
            if (next.equals(getLocation())) {
                return false;
            } else {
                setLocation(next);
            }
        }
        dungeonMap.UpdateEntity(this);
        return true;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        Collection<Entity> entities = dungeonMap.getEntities(player.getLocation(), this.bribe_radius);
        if (entities.stream().anyMatch(entity -> entity.getType().equals("mercenary"))) {

            if (player.getInventory().removeFromInventoryList("treasure", this.bribe_amount)) {
                setType("ally");
                dungeonMap.removeEntity(getEntityId());
                dungeonMap.addEntity(new MercenaryAlly("ally", getLocation(), ally_attack, ally_defence));
                return true;
            }
            return false;
        }
        return false;
    }
}
