package dungeonmania.movingEntities;

import dungeonmania.PotionEffectObserver;
import dungeonmania.PotionEffectSubject;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.Movement;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.movementStrategies.ChaseMovement;

public class Mercenary extends MovingEntity implements Movement, PotionEffectObserver {
    private int bribe_amount;
    private int bribe_radius;
    private double ally_attack;
    private double ally_defence;

    public Mercenary(String type, Location location, double mercenary_attack, double mercenary_health, int bribe_amount, int bribe_radius, double ally_attack, double ally_defence) {
        super(type, location, mercenary_health, new BaseAttackStrategy(mercenary_attack), new ChaseMovement(location));
        this.bribe_amount = bribe_amount;
        this.bribe_radius = bribe_radius;
        this.ally_attack = ally_attack;
        this.ally_defence = ally_defence;
    }
    
    public int getBribe_amount() {
        return bribe_amount;
    }

    public int getBribe_radius() {
        return bribe_radius;
    }

    public double getAlly_attack() {
        return ally_attack;
    }

    public double getAlly_defence() {
        return ally_defence;
    }

    @Override
    public void update(PotionEffectSubject s) {
        
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        return false;
    }

    // public abstract boolean interact(Player player, DungeonMap dungeonMap);
   
}
