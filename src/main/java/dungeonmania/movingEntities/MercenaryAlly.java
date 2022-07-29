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
    
    private int ally_attack;
    private int ally_defence;
    private double assassin_bribe_fail_rate;
    private int assassin_recon_radius;
    public MercenaryAlly(Mercenary mercenary) {
        super(mercenary.getType(), mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribe_amount(), mercenary.getBribe_radius(), mercenary.getAlly_attack(), mercenary.getAlly_defence());
            System.out.println("NEW NEW NEW");
    }

    public MercenaryAlly(Mercenary mercenary, double assassin_bribe_fail_rate, int assassin_recon_radius) {
        super(mercenary.getType(), mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribe_amount(), mercenary.getBribe_radius(), mercenary.getAlly_attack(), mercenary.getAlly_defence());
            this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
            this.assassin_recon_radius = assassin_recon_radius;
            System.out.println("NEW NEW NEW");
    }
    
    public double getFailRate() {
        return this.assassin_bribe_fail_rate;
    }

    public int getReconRadius() {
        return this.assassin_recon_radius;
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        System.out.println("Ally MOveing");
        Player p = dungeonMap.getPlayer();
        String options = MovementOptions.encodeLocationsArguments(dungeonMap, this);
        if (p.getLocation().equals(getLocation())) {
            setMove(new FollowingMovement(p.getPreviousLocation()));
        } 
        Location next = getMove().MoveOptions(options).nextLocation(p.getLocation(), dungeonMap);
        if (getMove() instanceof RandomMovement) {
            next = getMove().MoveOptions(options).nextLocation(getLocation(), dungeonMap);
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
    public void SceptreUpdate(SceptreEffectSubject subject, DungeonMap dungeonMap) {
        System.out.println("------------------- change to enemy -------------------");
        Mercenary enemy;
        if (this.getType().equals("mercenary")) {
            enemy = new MercenaryEnemy(this);
        } else {
            enemy = new Assassin(this, getFailRate(), getReconRadius());
        }
        enemy.setEntityId(String.valueOf(getEntityId()));
        dungeonMap.removeEntity(getEntityId());
        System.out.println("ENEMY POSITION: "+enemy.getLocation());
        System.out.println("ENEMY Type: "+ enemy.getType());
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
        // TODO Auto-generated method stub
        return null;
    }
}
