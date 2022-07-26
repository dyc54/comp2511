package dungeonmania.movingEntities;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.movementStrategies.ChaseMovement;
import dungeonmania.strategies.movementStrategies.MovementOptions;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class MercenaryEnemy extends Mercenary implements Enemy {
    public MercenaryEnemy(String type, Location location, double mercenary_attack, double mercenary_health,
            int bribe_amount, int bribe_radius, int ally_attack, int ally_defence) {
        super("mercenary", location, mercenary_attack, mercenary_health, bribe_amount, bribe_radius, ally_attack, ally_defence);
    }

	@Override
	public AttackStrategy getAttackStrayegy() {
		return getAttack();
	}
	@Override
	public String getEnemyId() {
		return getEntityId();
	}
	@Override
	public String getEnemyType() {
		return getType();
	}
	
    
    @Override
    public boolean movement(DungeonMap dungeonMap) {
        Location playerLocation = new Location();
        Player p = dungeonMap.getPlayer();
        playerLocation = p.getLocation();
        Location next = new Location();
        String choice = MovementOptions.encodeLocationsArguments(dungeonMap, this);
        if (getMove() instanceof RandomMovement) {
            next = getMove().MoveOptions(choice).nextLocation(getLocation());
        } else {
            next = getMove().MoveOptions(choice).nextLocation(playerLocation);
        }
        System.out.println(String.format("Movement: E Mercenary %s -> %s", getLocation(), next));
        setLocation(next);
        dungeonMap.UpdateEntity(this);
        return false;

    }
    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        player.getInventory().print();
        System.out.println(player.getInventory().countItem("treasure") >= super.getBribe_amount() );
        System.out.println(player.getLocation().distance(getLocation()) <= getBribe_radius());
        if (player.getInventory().countItem("treasure") >= super.getBribe_amount() 
            && player.getLocation().distance(getLocation()) <= getBribe_radius()) {
            MercenaryAlly ally = new MercenaryAlly(this);
            ally.setEntityId(String.valueOf(getEntityId()));
            dungeonMap.removeEntity(getEntityId());
            dungeonMap.addEntity(ally);
            System.out.println("pay for mercenary");
            
            player.getAttackStrategy().bonusDamage(ally);
            player.getDefenceStrayegy().bonusDefence(ally);
            player.getInventory().removeFromInventoryList("treasure", super.getBribe_amount());
            player.attach(ally);
            return true;
        }
        return false;
    }

    @Override
    public void update(PotionEffectSubject subject) {
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
