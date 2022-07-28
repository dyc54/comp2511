package dungeonmania.bosses;

import java.util.Random;

import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.MercenaryAlly;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;
import dungeonmania.strategies.movementStrategies.ChaseMovement;
import dungeonmania.strategies.movementStrategies.MovementOptions;
import dungeonmania.strategies.movementStrategies.RandomMovement;

public class Assassin extends Mercenary implements Enemy{

    private double assassin_bribe_fail_rate;
    private int assassin_recon_radius;

    public Assassin(String type, Location location, double health, int attack, int bribe_amount, int bribe_radius, int ally_attack, int ally_defence, double assassin_bribe_fail_rate, int assassin_recon_radius) {
        super(type, location, attack, health, bribe_amount, bribe_radius, ally_attack, ally_defence);
        this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
        this.assassin_recon_radius = assassin_recon_radius;
    }

    public Assassin(MercenaryAlly mercenary, double assassin_bribe_fail_rate, int assassin_recon_radius) {
        super("assassin", mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribe_amount(), mercenary.getBribe_radius(), mercenary.getAlly_attack(), mercenary.getAlly_defence());
        this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
        this.assassin_recon_radius = assassin_recon_radius;
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        Player p = dungeonMap.getPlayer();
        Location playerLocation = p.getLocation();
        String choice = MovementOptions.encodeLocationsArguments(dungeonMap, this);
        Location next = new Location();
        if (!CheckMovementFactor()) {
            return false;
        }
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
    public void update(PotionEffectSubject s) {
        if (s instanceof Player) {
            update((Player) s);
        }   
    }
    public void update(Player player) {
        double distance = player.getLocation().distance(getLocation());
        System.out.println("DISTANCE: "+distance);
        if (player.hasEffect() 
        && player.getCurrentEffect().applyEffect().equals("Invisibility")
        && distance > assassin_recon_radius) {
            System.out.println("RANDOMMOVEMENT");
            setMove(new RandomMovement());
        } else {
            setMove(new ChaseMovement(getLocation()));
        }
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        Random random = new Random(getLocation().hashCode());
        double rate = random.nextDouble();
        System.out.println("RATE: "+rate);
        System.out.println("FAIL_RATE: "+assassin_bribe_fail_rate);
        if (player.getInventory().countItem("treasure") >= super.getBribe_amount()
            && player.getLocation().distance(getLocation()) <= getBribe_radius()
            && rate >= assassin_bribe_fail_rate) {
            MercenaryAlly ally = new MercenaryAlly(this);
            ally.setEntityId(String.valueOf(getEntityId()));
            dungeonMap.removeEntity(getEntityId());
            dungeonMap.addEntity(ally);
            player.getAttackStrategy().bonusDamage(ally);
            player.getDefenceStrayegy().bonusDefence(ally);
            player.getInventory().removeFromInventoryList("treasure", super.getBribe_amount(), player);
            player.attach(ally);
            return true;
        }
        return false;
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

    
}
