package dungeonmania.Battle;

import dungeonmania.Strategies.AttackStrategies.AttackStrategy;

public interface Enemy {
    public AttackStrategy getAttackStrayegy();
    public double getHealth();
    public void setHealth(double health);
    public String getEnemyId();
    public String getEnemyType();
    // public 
}
