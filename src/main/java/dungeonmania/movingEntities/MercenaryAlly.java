package dungeonmania.movingEntities;

import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;
import dungeonmania.strategies.movementStrategies.ChaseMovement;
import dungeonmania.strategies.movementStrategies.FollowingMovement;
import dungeonmania.strategies.movementStrategies.MovementStrategy;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class MercenaryAlly extends Mercenary implements BonusDamageAdd, BonusDefenceAdd{
    
    private int ally_attack;
    private int ally_defence;
    public MercenaryAlly(MercenaryEnemy mercenary) {
        super("mercenary", mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribe_amount(), mercenary.getBribe_radius(), mercenary.getAlly_attack(), mercenary.getAlly_defence());
            System.out.println("NEW NEW NEW");
        
        }
    
    @Override
    public boolean movement(DungeonMap dungeonMap) {
        System.out.println("Ally MOveing");
        Player p = dungeonMap.getPlayer();
        String options = MovingEntity.getPossibleNextDirection(dungeonMap, this);
        if (p.getLocation().equals(getLocation())) {
            setMove(new FollowingMovement(p.getPreviousLocation()));
        } 
        Location next = getMove().MoveOptions(options).nextLocation(p.getLocation());
        if (getMove() instanceof RandomMovement) {
            next = getMove().MoveOptions(options).nextLocation(getLocation());
        }
        if (p.getLocation().equals(next)) {
            setMove(new FollowingMovement(p.getPreviousLocation()));
        } 
        System.out.println(String.format("Movement: Mercenary %s -> %s", getLocation(), next));

        setLocation(next);
        
        dungeonMap.UpdateEntity(this);
        return true;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        return true;
    }

    @Override
    public double damage() {
        return ally_attack;
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        return this == obj;
    }

    @Override
    public double defence() {
        return ally_defence;
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        return this == obj;
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
        } 
        if (!player.hasEffect()) {
            if (player.getLocation().equals(getLocation())) {
                setMove(new FollowingMovement(player.getPreviousLocation()));
            } else {
                setMove(new ChaseMovement(getLocation()));
            }
        }
    }
}
