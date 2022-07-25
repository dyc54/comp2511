package dungeonmania.strategies.attackStrategies;

import java.util.Random;

public class ChanceAttackStrategy implements AttackStrategy{

    private double attack;
    private double rate;
    private int amount;

    public ChanceAttackStrategy(double attack, double rate, int amount) {
        this.attack = attack;
        this.rate = rate;
        this.amount = amount;
    }
    
    @Override
    public double attackDamage() {
        return attack;
    }

    public boolean isAddHealth() {
        Random r = new Random();
        return r.nextDouble() < this.rate; 
            
    }

    public int getIncreaseAmount() {
        return (isAddHealth())? amount:0;
    }

    @Override
    public void bonusDamage(BonusDamageAdd attack) {
        
    }

    @Override
    public void bonusDamage(BonusDamageMul attack) {
        
    }

    @Override
    public void removeBounus(BonusDamageAdd attack) {
        
    }

    @Override
    public void removeBounus(BonusDamageMul attack) {
        
    }
    
}
