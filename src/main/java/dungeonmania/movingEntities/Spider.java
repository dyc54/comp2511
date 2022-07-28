package dungeonmania.movingEntities;

import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.EnemyMovement;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.movementStrategies.CircleMovement;
import dungeonmania.strategies.movementStrategies.MovementStrategy;

public class Spider extends MovingEntity implements EnemyMovement, Enemy {

    /**
     * Constructor for spider class
     * 
     * @param location
     * @param spider_attack
     * @param spider_health
     */
    public Spider(String type, Location location, int spider_attack, int spider_health) {
        super(type, location, spider_health, new BaseAttackStrategy(spider_attack), new CircleMovement(location));
    }
    private boolean checkhasBoulder(DungeonMap dungeonMap, Location next) {
        return dungeonMap.getEntities(next).stream().anyMatch(entity -> entity.getType().equals("boulder"));

    }
    @Override
    public boolean movement(DungeonMap dungeonMap) {
        Location current = getLocation();
        MovementStrategy strategy = super.getMove();
        Location next = strategy.nextLocation(current);
        if (!CheckMovementFactor()) {
            return false;
        }
        if (checkhasBoulder(dungeonMap, next)) {
            Location temp = strategy.MoveOptions("CHANGE_DIRECTION").nextLocation(current);
            if (checkhasBoulder(dungeonMap, temp)) {
                return false;
            } else {
                System.out.println("reverse next");
                next = temp;
            }
        }
        setLocation(next);
        dungeonMap.UpdateEntity(this);
        return true;
    }


    @Override
    public AttackStrategy getAttackStrayegy() {
        return super.getAttack();
    }

    @Override
    public double getHealth() {
        return super.getHealth();
    }

    @Override
    public String getEnemyId() {
        return getEntityId();
    }
    @Override
    public String getEnemyType() {
        return getType();
    }


}
