package dungeonmania.movingEntities;

import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.SceptreEffectObserver;
import dungeonmania.SceptreEffectSubject;
import dungeonmania.bosses.Assassin;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;
import dungeonmania.strategies.movementStrategies.ChaseMovement;
import dungeonmania.strategies.movementStrategies.FollowingMovement;
import dungeonmania.strategies.movementStrategies.MovementOptions;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class MercenaryAlly extends Mercenary implements BonusDamageAdd, BonusDefenceAdd, SceptreEffectObserver, Interact{
    
    private int allyAttack;
    private int allyDefence;
    private double assassinBribeFailRate;
    private int assassinReconRadius;
    public MercenaryAlly(Mercenary mercenary) {
        super(mercenary.getType(), mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribeAmount(), mercenary.getBribeRadius(), mercenary.getAllyAttack(), mercenary.getAllyDefence());
    }

    public MercenaryAlly(Mercenary mercenary, double assassinBribeFailRate, int assassinReconRadius) {
        super(mercenary.getType(), mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribeAmount(), mercenary.getBribeRadius(), mercenary.getAllyAttack(), mercenary.getAllyDefence());
            this.assassinBribeFailRate = assassinBribeFailRate;
            this.assassinReconRadius = assassinReconRadius;
    }
    
    public double getFailRate() {
        return this.assassinBribeFailRate;
    }

    public int getReconRadius() {
        return this.assassinReconRadius;
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        Player p = dungeonMap.getPlayer();
        String options = MovementOptions.encodeLocationsArguments(dungeonMap, this);
        if (!CheckMovementFactor()) {
            return false;
        }
        if (p.getLocation().equals(getLocation())) {
            setMove(new FollowingMovement(p.getPreviousLocation()));
        } 
        Location next = getMove().MoveOptions(options).nextLocation(p.getLocation(), dungeonMap);
        if (getMove() instanceof RandomMovement) {
            next = getMove().MoveOptions(options).nextLocation(getLocation(), dungeonMap);
        }
        if (!CheckMovementFactor()) {
            return false;
        }
        if (p.getLocation().equals(next)) {
            setMove(new FollowingMovement(p.getPreviousLocation()));
        } 

        setLocation(next);
        dungeonMap.interactAll(this);

        
        dungeonMap.UpdateEntity(this);
        return true;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        return true;
    }

    @Override
    public double damage() {
        return allyAttack;
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        return this == obj;
    }

    @Override
    public double defence() {
        return allyDefence;
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        return this == obj;
    }

    @Override
    public void SceptreUpdate(SceptreEffectSubject subject, DungeonMap dungeonMap) {
        Mercenary enemy;
        if (this.getType().equals("mercenary")) {
            enemy = new MercenaryEnemy(this);
        } else {
            enemy = new Assassin(this, getFailRate(), getReconRadius());
        }
        enemy.setEntityId(String.valueOf(getEntityId()));
        dungeonMap.removeEntity(getEntityId());
        dungeonMap.addEntity(enemy);
        Player player = dungeonMap.getPlayer();
        player.getAttackStrategy().removeBounus(this);
        player.getDefenceStrayegy().removeDefence(this);
        player.attach(enemy);
        player.detach(this);
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

    @Override
    public ItemResponse toItemResponse() {
        return null;
    }
}
