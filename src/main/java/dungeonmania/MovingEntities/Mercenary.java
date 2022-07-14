package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.Battle.Enemy;
import dungeonmania.CollectableEntities.DurabilityEntities.InvincibilityPotion;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.FollowingMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class Mercenary extends MovingEntity implements EnemyMovement, Interact, Enemy {
    // Location location;
    double mercenary_attack;
    double mercenary_health;
    int bribe_amount;
    int bribe_radius;
    int ally_attack;
    int ally_defence;
    boolean hasBribed;
    public Mercenary(String type, Location location, double mercenary_attack, double mercenary_health, int bribe_amount, int bribe_radius, int ally_attack, int ally_defence, boolean hasBribed) {
        super(type, location, mercenary_health, new BaseAttackStrategy(mercenary_attack), new ChaseMovement(location));
        this.mercenary_attack = mercenary_attack;
        this.mercenary_health = mercenary_health;
        this.bribe_amount = bribe_amount;
        this.bribe_radius = bribe_radius;
        this.ally_attack = ally_attack;
        this.ally_defence = ally_defence;
        this.hasBribed = hasBribed;
    }


	@Override
    public boolean movement(DungeonMap dungeonMap) {
        MovementStrategy strategy = super.getMove();
        Location playerLocation = new Location();
        Player p = dungeonMap.getPlayer();
        playerLocation = p.getLocation();
        Location next = new Location();
        if (p.getCurrentEffect() != null) { //Check for invisibility 
            if (p.getCurrentEffect().applyEffect().equals("Invisibility")) {
                strategy = new RandomMovement();
                String choice = MovingEntity.getPossibleNextDirection(dungeonMap, this);
                next = strategy.MoveOptions(choice).nextLocation(getLocation());
                if (next.equals(getLocation())) {
                    return false;
                }
            }
        }else if (isHasBribed() && dungeonMap.getFourNearEntities(p.getLocation()).contains(this)) {
            strategy = new FollowingMovement();
            Location playerPreLocation = p.getPreviousLocation();
            next = strategy.nextLocation(playerPreLocation);
        } else {
            next = strategy.nextLocation(playerLocation);
            if (dungeonMap.checkMovement(next)) {
                next = strategy.moveWithWall(next, dungeonMap);
                if (next.equals(getLocation())) {
                    return false;
                }
            }
        }
        setLocation(next);
        dungeonMap.UpdateEntity(this);
        return true;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        Collection<Entity> entities = dungeonMap.getEntities(player.getLocation(), this.bribe_radius);
        if (entities.stream().anyMatch(entity -> entity.getType().equals("mercenary"))) {
            if (player.getInventory().removeFromInventoryList("treasure", this.bribe_amount)) {
                MercenaryAlly ally = new MercenaryAlly("ally", getLocation(), ally_attack, ally_defence, getHealth());
                dungeonMap.addEntity(ally);
                setHasBribed(true);
                player.getAttackStrayegy().bonusDamage(ally);
                player.getDefenceStrayegy().bonusDefence(ally);
                // player.getInventory().r
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public AttackStrayegy getAttackStrayegy() {
        // TODO Auto-generated method stub
        return getAttack();
    }

    @Override
    public String getEnemyId() {
        // TODO Auto-generated method stub
        return super.getEntityId();
    }

    @Override
    public String getEnemyType() {
        // TODO Auto-generated method stub
        return getType();
    }

    @Override
    public double getHealth() {
        return super.getHealth();
    }

    public boolean isHasBribed() {
        return hasBribed;
    }

    public void setHasBribed(boolean hasBribed) {
        this.hasBribed = hasBribed;
    }

}
