package dungeonmania.strategies.battleStrategies;

public interface BattleStrategy {
    // public double getHealth();
    public boolean subHealth(double damage);
    public boolean isAlive();
}
