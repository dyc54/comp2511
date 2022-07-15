package dungeonmania.MovingEntities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.Battle.Enemy;
import dungeonmania.CollectableEntities.DurabilityEntities.InvincibilityPotion;
import dungeonmania.Strategies.Movement;
import dungeonmania.Strategies.AttackStrategies.AttackStrategy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.AttackStrategies.BonusDamageAdd;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.FollowingMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public abstract class Mercenary extends MovingEntity implements Movement {
    // Location location;
    // private double mercenary_attack;
    // private double mercenary_health;
    private int bribe_amount;
    private int bribe_radius;
    private int ally_attack;
    private int ally_defence;
    // private AttackStrategy attackStrategy;

    public Mercenary(String type, Location location, double mercenary_attack, double mercenary_health, int bribe_amount, int bribe_radius, int ally_attack, int ally_defence) {
        super(type, location, mercenary_health, new BaseAttackStrategy(mercenary_attack), new ChaseMovement(location));
        this.bribe_amount = bribe_amount;
        this.bribe_radius = bribe_radius;
        this.ally_attack = ally_attack;
        this.ally_defence = ally_defence;
        //this.moveStrategy = super.getMove();
    }

    // public MovementStrategy getMoveStrategy() {
    //     return this.moveStrategy;
    // }

    // public AttackStrategy getAttackStrage(){
    //     return this.attackStrategy;
    // }
    
    public int getBribe_amount() {
        return bribe_amount;
    }

    public int getBribe_radius() {
        return bribe_radius;
    }

    public int getAlly_attack() {
        return ally_attack;
    }

    public int getAlly_defence() {
        return ally_defence;
    }

    // public abstract boolean movement(DungeonMap dungeonMap);
    public abstract boolean interact(Player player, DungeonMap dungeonMap);
    // public boolean movement(DungeonMap dungeonMap) {
    //     Location playerLocation = new Location();
    //     Player p = dungeonMap.getPlayer();
    //     playerLocation = p.getLocation();
    //     Location next = new Location();
    //     if (p.getLocation().equals(getLocation())) {
    //         isMeet = true;
    //         return false;
    //     }
    //     if (p.getCurrentEffect() != null) { //Check for invisibility 
    //         if (p.getCurrentEffect().applyEffect().equals("Invisibility")) {
    //             changeStrategy(new RandomMovement());
    //             String choice = MovingEntity.getPossibleNextDirection(dungeonMap, this);
    //             next = strategy.MoveOptions(choice).nextLocation(getLocation());
    //             if (next.equals(getLocation())) {
    //                 return false;
    //             }
    //         }
    //     }else if (isMeet) {
    //         changeStrategy(new FollowingMovement());;
    //         Location playerPreLocation = p.getPreviousLocation();
    //         next = strategy.nextLocation(playerPreLocation);
    //     } else {
    //         next = strategy.nextLocation(playerLocation);
    //         if (dungeonMap.checkMovement(next)) {
    //             next = strategy.moveWithWall(next, dungeonMap);
    //             if (next.equals(getLocation())) {
    //                 return false;
    //             }
    //         }
    //     }
    //     setLocation(next);
    //     dungeonMap.UpdateEntity(this);
    //     return true;
    // }

    // public void changeStrategy(MovementStrategy newStrategy) {
    //     this.moveStrategy = newStrategy;
    // }

    // public boolean interact(Player player, DungeonMap dungeonMap) {
    //     Collection<Entity> entities = dungeonMap.getEntities(player.getLocation(), this.bribe_radius);
    //     if (entities.stream().anyMatch(entity -> entity.getType().equals("mercenary"))) {
    //         if (player.getInventory().removeFromInventoryList("treasure", this.bribe_amount)) {
    //             MercenaryAlly ally = (MercenaryAlly) this;
    //             dungeonMap.removeEntity(getEntityId());
    //             dungeonMap.addEntity(ally);
    //             player.getAttackStrayegy().bonusDamage(ally);
    //             player.getDefenceStrayegy().bonusDefence(ally);
    //             // player.getInventory().r
    //             return true;
    //         }
    //         return false;
    //     }
    //     return false;
    // }

    // @Override
    // public AttackStrayegy getAttackStrayegy() {
    //     // TODO Auto-generated method stub
    //     return getAttack();
    // }

    // @Override
    // public String getEnemyId() {
    //     // TODO Auto-generated method stub
    //     return super.getEntityId();
    // }

    // @Override
    // public String getEnemyType() {
    //     // TODO Auto-generated method stub
    //     return getType();
    // }

    // @Override
    // public double getHealth() {
    //     return super.getHealth();
    // }
}
