package dungeonmania.MovingEntities;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.AttackStrategies.AttackStrategy;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

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
        String choice = MovingEntity.getPossibleNextDirection(dungeonMap, this);
        next = getMove().MoveOptions(choice).nextLocation(playerLocation);
        System.out.println(String.format("Movement: E Mercenary %s -> %s", getLocation(), next));
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
                return true;
            }
            return false;
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
