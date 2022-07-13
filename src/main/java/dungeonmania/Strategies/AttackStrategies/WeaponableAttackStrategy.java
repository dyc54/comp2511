package dungeonmania.Strategies.AttackStrategies;

import java.util.ArrayList;
import java.util.List;

public class WeaponableAttackStrategy implements AttackStrayegy{
    private BaseAttackStrategy base;
    private List<BonusDamageMul> bonusdamages;
    public WeaponableAttackStrategy(int base) {
        this.base = new BaseAttackStrategy(base);
        bonusdamages = new ArrayList<>();
    }
    @Override
    public double attackDamage() {
        return base.attackDamage() * bonusdamages.stream()
                                                .map(bonus -> {return Double.valueOf(bonus.damage());})
                                                .mapToDouble(Double::doubleValue)
                                                .reduce(1.0, (a, b) -> a * b);
    }
    @Override
    public void bonusDamage(BonusDamageAdd attack) {
        base.bonusDamage(attack);
        
    }
    @Override
    public void bonusDamage(BonusDamageMul attack) {
        bonusdamages.add(attack);
        
    }
    @Override
    public void removeBounus(BonusDamageAdd attack) {
        base.removeBounus(attack);
        
    }
    @Override
    public void removeBounus(BonusDamageMul attack) {
        bonusdamages.remove(attack);
        
    } 
}
