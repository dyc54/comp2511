package dungeonmania.Battle;

import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;

public interface Enemy {
    public AttackStrayegy getAttackStrayegy();
    public double getHealth();
    // public double subHealth();
    public String getEnemyId();
}
