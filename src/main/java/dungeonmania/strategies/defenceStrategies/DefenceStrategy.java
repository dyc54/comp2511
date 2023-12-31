package dungeonmania.strategies.defenceStrategies;

public interface DefenceStrategy {
    public double defenceDamage();
    public void bonusDefence(BonusDefenceAdd defence);
    public void removeDefence(BonusDefenceAdd defence);
}
