package dungeonmania.strategies.battleStrategies;

import dungeonmania.Player;

public interface BattleStrategyWithPlayer extends BattleStrategy{
    public boolean battleWith(Player player);
    public double battleDamageFrom(Player player);
}
