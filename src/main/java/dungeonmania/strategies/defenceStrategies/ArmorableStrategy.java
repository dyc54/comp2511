package dungeonmania.strategies.defenceStrategies;

import java.util.ArrayList;
import java.util.List;

public class ArmorableStrategy implements DefenceStrategy {
    private int base;
    private List<BonusDefenceAdd> bonusdefence;
    public ArmorableStrategy(int base) {
        this.base = base;
        bonusdefence = new ArrayList<>();
    }
    @Override
    public double defenceDamage() {
        return base + bonusdefence.stream()
                                    .map(bonus -> {return Double.valueOf(bonus.defence());})
                                    .mapToDouble(Double::doubleValue).sum();
    }
    @Override
    public void bonusDefence(BonusDefenceAdd defence) {
        bonusdefence.add(defence);
    }
    @Override
    public void removeDefence(BonusDefenceAdd defence) {
        bonusdefence.remove(defence);
    }
}
