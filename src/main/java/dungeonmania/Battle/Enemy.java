package dungeonmania.Battle;

import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;

public interface Enemy {
    public AttackStrayegy getAttackStrayegy();
    public double getHealth();
    public void setHealth(double health);
    public String getEnemyId();
    public String getEnemyType();
    // public 
}
