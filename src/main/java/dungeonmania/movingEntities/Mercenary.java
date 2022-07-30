package dungeonmania.movingEntities;

import dungeonmania.PotionEffectObserver;
import dungeonmania.PotionEffectSubject;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.strategies.Movement;
import dungeonmania.strategies.attackStrategies.BaseAttackStrategy;
import dungeonmania.strategies.movementStrategies.ChaseMovement;

public class Mercenary extends MovingEntity implements Movement, PotionEffectObserver {
    private int bribeAmount;
    private int bribeRadius;
    private double allyAttack;
    private double allyDefence;

    public Mercenary(String type, Location location, double mercenaryAttack, double mercenaryHealth, int bribeAmount, int bribeRadius, double allyAttack, double allyDefence) {
        super(type, location, mercenaryHealth, new BaseAttackStrategy(mercenaryAttack), new ChaseMovement(location));
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
    }
    
    public int getBribeAmount() {
        return bribeAmount;
    }

    public int getBribeRadius() {
        return bribeRadius;
    }

    public double getAllyAttack() {
        return allyAttack;
    }

    public double getAllyDefence() {
        return allyDefence;
    }

    @Override
    public void update(PotionEffectSubject s) {
        
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        return false;
    }
   
}
