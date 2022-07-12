package dungeonmania.Strategies.AttackStrategies;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BaseAttackStrategy implements AttackStrayegy{
    private double attack;
    private List<BonusDamageAdd> bonusdamages; 
    public BaseAttackStrategy(double attack) {
        this.attack = attack;
        this.bonusdamages = new ArrayList<>();
    }

    @Override
    public double attackDamage() {
        return attack + bonusdamages.stream()
                                .map(bonus -> {return Double.valueOf(bonus.damage());})
                                .mapToDouble(Double::doubleValue).sum();
    }
    @Override
    public void bonusDamage(BonusDamageAdd attack) {
        bonusdamages.add(attack);
        
    }

    @Override
    public void bonusDamage(BonusDamageMul attack) {
        // DO NOTHING
    }

    @Override
    public void removeAddBounus(BonusDamageAdd attack) {
        bonusdamages.remove(attack);
        
    }

    @Override
    public void removeMulBounus(BonusDamageMul attack) {
        // DO NOTHING
    }
}
