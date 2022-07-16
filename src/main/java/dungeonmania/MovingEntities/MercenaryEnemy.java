package dungeonmania.movingEntities;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.EnemyMovement;
import dungeonmania.strategies.Movement;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.movementStrategies.ChaseMovement;
import dungeonmania.strategies.movementStrategies.MovementStrategy;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class MercenaryEnemy extends Mercenary implements Enemy {
    // private double mercenary_attack;
    // private double mercenary_health;
    public MercenaryEnemy(String type, Location location, double mercenary_attack, double mercenary_health,
            int bribe_amount, int bribe_radius, int ally_attack, int ally_defence) {
        super("mercenary", location, mercenary_attack, mercenary_health, bribe_amount, bribe_radius, ally_attack, ally_defence);
        // this.mercenary_attack = mercenary_attack;
        // this.mercenary_health = mercenary_health;
    }

	@Override
	public AttackStrategy getAttackStrayegy() {
		// TODO Auto-generated method stub
		return getAttack();
	}
	@Override
	public String getEnemyId() {
		// TODO Auto-generated method stub
		return getEntityId();
	}
	@Override
	public String getEnemyType() {
		// TODO Auto-generated method stub
		return getType();
	}
	
    
    @Override
    public boolean movement(DungeonMap dungeonMap) {
        Location playerLocation = new Location();
        Player p = dungeonMap.getPlayer();
        playerLocation = p.getLocation();
        Location next = new Location();
        // if (p.hasEffect()) { //Check for invisibility 
        //     if (p.getCurrentEffect().applyEffect().equals("Invisibility")) {
        //         setMove(new RandomMovement());
        //         String choice = MovingEntity.getPossibleNextDirection(dungeonMap, this);
        //         next = getMove().MoveOptions(choice).nextLocation(getLocation());
        //         if (next.equals(getLocation())) {
        //             return false;
        //         }
        //     }
        // } else {
        //     setMove(new ChaseMovement(getLocation()));
        //     next = getMove().nextLocation(playerLocation);
        //     if (dungeonMap.checkMovement(next)) {
        //         next = getMove().moveWithWall(next, dungeonMap);
        //         if (next.equals(getLocation())) {
        //             return false;
        //         }
        //     }
        // }
        String choice = MovingEntity.getPossibleNextDirection(dungeonMap, this);
        next = getMove().MoveOptions(choice).nextLocation(playerLocation);
        // if (dungeonMap.checkMovement(next)) {
        //     next = getMove().moveWithWall(next, dungeonMap);
        //     if (next.equals(getLocation())) {
        //         return false;
        //     }
        System.out.println(String.format("Movement: E Mercenary %s -> %s", getLocation(), next));
        // }
        setLocation(next);
        dungeonMap.UpdateEntity(this);
        return false;

    }
    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        Collection<Entity> entities = dungeonMap.getEntities(player.getLocation(), super.getBribe_radius());
        if (entities.stream().anyMatch(entity -> entity.getType().equals("mercenary"))) {
            
            if (player.getInventory().countItem("treasure") >= super.getBribe_amount() 
                && player.getInventory().removeFromInventoryList("treasure", super.getBribe_amount())) {
                MercenaryAlly ally = new MercenaryAlly(this);
                ally.setEntityId(String.valueOf(getEntityId()));
                dungeonMap.removeEntity(getEntityId());
                dungeonMap.addEntity(ally);
                System.out.println("pay for mercenary");
                
                player.getAttackStrategy().bonusDamage(ally);
                player.getDefenceStrayegy().bonusDefence(ally);
                player.attach(ally);
                // player.getInventory().r
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public void update(PotionEffectSubject subject) {
        // TODO Auto-generated method stub
        if (subject instanceof Player) {
            update((Player) subject);
        }
        
    }
    public void update(Player player) {
        if (player.hasEffect() && player.getCurrentEffect().applyEffect().equals("Invisibility")) {
            setMove(new RandomMovement());
        } else {
            setMove(new ChaseMovement(getLocation()));
        }
    }
	
}
