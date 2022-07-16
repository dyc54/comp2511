package dungeonmania.movingEntities;

import dungeonmania.Player;
import dungeonmania.PotionEffecObserver;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.Movement;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.movementStrategies.ChaseMovement;

public abstract class Mercenary extends MovingEntity implements Movement, PotionEffecObserver {
    private int bribe_amount;
    private int bribe_radius;
    private int ally_attack;
    private int ally_defence;

    public Mercenary(String type, Location location, double mercenary_attack, double mercenary_health, int bribe_amount, int bribe_radius, int ally_attack, int ally_defence) {
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

    public int getAlly_attack() {
        return ally_attack;
    }

    public int getAlly_defence() {
        return ally_defence;
    }

    public abstract boolean interact(Player player, DungeonMap dungeonMap);
   
}
