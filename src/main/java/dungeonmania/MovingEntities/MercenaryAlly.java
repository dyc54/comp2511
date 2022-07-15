package dungeonmania.MovingEntities;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.AttackStrategy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.AttackStrategies.BonusDamageAdd;
import dungeonmania.Strategies.DefenceStrategies.BonusDefenceAdd;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.FollowingMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class MercenaryAlly extends Mercenary implements EnemyMovement, BonusDamageAdd, BonusDefenceAdd{
    
    private int ally_attack;
    private int ally_defence;
    private boolean isMeet;
    public MercenaryAlly(String type, Location location, double mercenary_attack, double mercenary_health,
            int bribe_amount, int bribe_radius, int ally_attack, int ally_defence, boolean isMeet) {
        super(type, location, mercenary_attack, mercenary_health, bribe_amount, bribe_radius, ally_attack, ally_defence);
        this.ally_defence = ally_defence;
        this.isMeet = isMeet;
    }

    public void setMeet(){
        this.isMeet = true;
    }
    
    @Override
    public boolean movement(DungeonMap dungeonMap) {
        Location playerLocation = new Location();
        Location next = new Location();
        Player p = dungeonMap.getPlayer();
        playerLocation = p.getLocation();
        System.out.println("playerLocation:"+playerLocation);
        System.out.println("MercenaryLocation:"+this.getLocation());
        if (p.getPreviousLocation().equals(getLocation())) {
            setMeet();
            super.changeStrategy(new FollowingMovement());
            return false;
        }
        if (isMeet) {
            changeStrategy(new FollowingMovement());
            Location playerPreLocation = p.getPreviousLocation();
            next = super.getMove().nextLocation(playerPreLocation);
        } else {
            next = getMove().nextLocation(playerLocation);
            if (dungeonMap.checkMovement(next)) {
                next = getMove().moveWithWall(next, dungeonMap);
                if (next.equals(getLocation())) {
                    return false;
                }
            }
        }
        setLocation(next);
        dungeonMap.UpdateEntity(this);
        return true;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        return true;
    }

    @Override
    public double damage() {
        // TODO Auto-generated method stub
        return getAttack().attackDamage();
    }

    @Override
    public boolean equals(BonusDefenceAdd obj) {
        // TODO Auto-generated method stub
        return this == obj;
    }

    @Override
    public double defence() {
        // TODO Auto-generated method stub
        return ally_defence;
    }

    @Override
    public boolean equals(BonusDamageAdd obj) {
        // TODO Auto-generated method stub
        return this == obj;
    }
    
}
