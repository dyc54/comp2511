package dungeonmania.bosses;

import java.util.Random;

import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.MercenaryAlly;
import dungeonmania.movingEntities.MercenaryEnemy;
;

public class Assassin extends MercenaryEnemy {

    private double assassinBribeFailRate;
    private int assassinReconRadius;

    public Assassin(String type, Location location, double health, double assassinAttack, int bribeAmount, int bribeRadius, double allyAttack, double allyDefence, double assassinBribeFailRate, int assassinReconRadius) {
        super(type, location, assassinAttack, health, bribeAmount, bribeRadius, allyAttack, allyDefence);
        this.assassinBribeFailRate = assassinBribeFailRate;
        this.assassinReconRadius = assassinReconRadius;
    }
    public Assassin(MercenaryAlly mercenary, double assassinBribeFailRate, int assassinReconRadius) {
        super(mercenary);
        this.assassinBribeFailRate = assassinBribeFailRate;
        this.assassinReconRadius = assassinReconRadius;
    }

    private boolean isRecon(Player player) {
        double distance = player.getLocation().distance(getLocation());
        return distance > assassinReconRadius;
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
        if (rate >= assassinBribeFailRate) {
            return super.interact(player, dungeonMap);
        } else {
            return player.canBribe(this);
        }
        
    }
    
}
