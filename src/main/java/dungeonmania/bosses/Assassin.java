package dungeonmania.bosses;

import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.strategies.attackStrategies.BonusDamageAdd;
import dungeonmania.strategies.defenceStrategies.BonusDefenceAdd;

public class Assassin extends Mercenary implements BonusDamageAdd, BonusDefenceAdd{

    public Assassin(String type, Location location, double health, int attack, int bribe_amount, int bribe_radius, int ally_attack, int ally_defence) {
        super(type, location, attack, health, bribe_amount, bribe_radius, ally_attack, ally_defence);
    }

    @Override
    public boolean movement(DungeonMap dungeonMap) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void update(PotionEffectSubject s) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double damage() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public double defence() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        // TODO Auto-generated method stub
        return false;
    }
    
}
