package dungeonmania.bosses;

import java.util.Random;

import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.battle.Enemy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.MercenaryAlly;
import dungeonmania.movingEntities.MercenaryEnemy;
;

public class Assassin extends MercenaryEnemy{

    // private double assassin_bribe_fail_rate;
    // private int assassin_recon_radius;

    // public Assassin(String type, Location location, double health, double attack, int bribe_amount, int bribe_radius, double ally_attack, double ally_defence, double assassin_bribe_fail_rate, int assassin_recon_radius) {
    //     super(type, location, attack, health, bribe_amount, bribe_radius, ally_attack, ally_defence);
    //     this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
    //     this.assassin_recon_radius = assassin_recon_radius;
    // }

    // public Assassin(MercenaryAlly mercenary, double assassin_bribe_fail_rate, int assassin_recon_radius) {
    //     super(mercenary.getType(), mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
    //             mercenary.getBribe_amount(), mercenary.getBribe_radius(), mercenary.getAlly_attack(), mercenary.getAlly_defence());
    //     this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
    //     this.assassin_recon_radius = assassin_recon_radius;
    // }

    
    private double assassin_bribe_fail_rate;
    private int assassin_recon_radius;

    public Assassin(String type, Location location, double health, double assassin_attack, int bribe_amount, int bribe_radius, double ally_attack, double ally_defence, double assassin_bribe_fail_rate, int assassin_recon_radius) {
        super(type, location, assassin_attack, health, bribe_amount, bribe_radius, ally_attack, ally_defence);
        this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
        this.assassin_recon_radius = assassin_recon_radius;
    }
    public Assassin(MercenaryAlly mercenary, double assassin_bribe_fail_rate, int assassin_recon_radius) {
        super(mercenary);
        this.assassin_bribe_fail_rate = assassin_bribe_fail_rate;
        this.assassin_recon_radius = assassin_recon_radius;
    }

    public boolean isRecon(Player player) {
        double distance = player.getLocation().distance(getLocation());
        return distance > assassin_recon_radius;
    }

    @Override
    public void update(PotionEffectSubject s) {
        if (s instanceof Player) {
            update((Player) s);
        }   
    }
    public void update(Player player) {
        if (isRecon(player)) {
            super.update(player);
        }
    }

    public double randomRate() {
        Random random = new Random(getLocation().hashCode());
        return random.nextDouble();
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        double rate = randomRate();
        if (rate >= assassin_bribe_fail_rate) {
            return super.interact(player, dungeonMap);
        } else {
            if (player.getInventory().countItem("treasure") >= super.getBribe_amount() 
            && player.getLocation().distance(getLocation()) <= getBribe_radius()) {
                player.getInventory().removeFromInventoryList("treasure", super.getBribe_amount(), player);
                return true;
            }
        }
        return false;
        
    }
    
}
