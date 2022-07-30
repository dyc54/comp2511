package dungeonmania.movingEntities;

import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.movementStrategies.ChaseMovement;
import dungeonmania.strategies.movementStrategies.MovementOptions;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class MercenaryEnemy extends Mercenary implements Enemy, Interact {
    
    public MercenaryEnemy(String type, Location location, double mercenary_attack, double mercenary_health,
            int bribe_amount, int bribe_radius, double ally_attack, double ally_defence) {
        super(type, location, mercenary_attack, mercenary_health, bribe_amount, bribe_radius, ally_attack, ally_defence);
    }

    public MercenaryEnemy(MercenaryAlly mercenary) {
        super(mercenary.getType(), mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribe_amount(), mercenary.getBribe_radius(), mercenary.getAlly_attack(), mercenary.getAlly_defence());
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
        if (!CheckMovementFactor()) {
            return false;
        }
        if (getMove() instanceof RandomMovement) {
            next = getMove().MoveOptions(choice).nextLocation(getLocation(), dungeonMap);
        } else {
            next = getMove().MoveOptions(choice).nextLocation(playerLocation, dungeonMap);
        }
        setLocation(next);
        dungeonMap.interactAll(this);
        dungeonMap.UpdateEntity(this);
        return false;

    }
    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        if (player.hasSceptre()) {
            return true;
        }
        if (player.canBribe(this)) {
            MercenaryAlly ally = new MercenaryAlly(this);
            ally.setEntityId(String.valueOf(getEntityId()));
            dungeonMap.removeEntity(getEntityId());
            dungeonMap.addEntity(ally);
            player.getAttackStrategy().bonusDamage(ally);
            player.getDefenceStrayegy().bonusDefence(ally);
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
        if (player.hasEffect()) {
        }
        if (player.hasEffect() && player.getCurrentEffect().applyEffect().equals("Invisibility")) {
            setMove(new RandomMovement());
        } else {
            setMove(new ChaseMovement(getLocation()));
        }
    }
	
}
