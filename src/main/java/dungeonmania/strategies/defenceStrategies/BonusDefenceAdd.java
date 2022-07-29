package dungeonmania.strategies.defenceStrategies;

import dungeonmania.strategies.BonusStrategy;

public interface BonusDefenceAdd extends BonusStrategy{
    public boolean equals(BonusDefenceAdd obj);
    public double defence();
}
