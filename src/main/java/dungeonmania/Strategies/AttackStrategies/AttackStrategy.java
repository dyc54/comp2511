package dungeonmania.Strategies.AttackStrategies;

public interface AttackStrategy {
    public double attackDamage();
    public void bonusDamage(BonusDamageAdd attack);
    public void bonusDamage(BonusDamageMul attack);
    public void removeBounus(BonusDamageAdd attack);
    public void removeBounus(BonusDamageMul attack);
}
