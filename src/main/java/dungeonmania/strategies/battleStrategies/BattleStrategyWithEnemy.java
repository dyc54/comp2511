package dungeonmania.strategies.battleStrategies;

import java.util.List;

import dungeonmania.battle.Enemy;

import dungeonmania.response.models.ItemResponse;
public interface BattleStrategyWithEnemy extends BattleStrategy{
    public boolean battleWith(Enemy enemy);
    public double battleDamageFrom(Enemy enemy);
    public List<ItemResponse> getBattleUsedItems();
}
