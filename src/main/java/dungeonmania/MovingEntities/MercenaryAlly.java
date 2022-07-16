package dungeonmania.MovingEntities;

import java.util.Collection;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.PotionEffectSubject;
import dungeonmania.Battle.Enemy;
import dungeonmania.Strategies.EnemyMovement;
import dungeonmania.Strategies.AttackStrategies.AttackStrategy;
import dungeonmania.Strategies.AttackStrategies.BaseAttackStrategy;
import dungeonmania.Strategies.AttackStrategies.BonusDamageAdd;
import dungeonmania.Strategies.DefenceStrategies.BonusDefenceAdd;
import dungeonmania.Strategies.MovementStrategies.ChaseMovement;
import dungeonmania.Strategies.MovementStrategies.FollowingMovement;
import dungeonmania.Strategies.MovementStrategies.MovementStrategy;
import dungeonmania.Strategies.MovementStrategies.RandomMovement;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class MercenaryAlly extends Mercenary implements BonusDamageAdd, BonusDefenceAdd{
    
    private int ally_attack;
    private int ally_defence;
    private boolean isFollowing;
    private boolean effecs;
    // public MercenaryAlly(String type, Location location, double mercenary_attack, double mercenary_health,
    //         int bribe_amount, int bribe_radius, int ally_attack, int ally_defence, boolean isMeet) {
    //     super(type, location, mercenary_attack, mercenary_health, bribe_amount, bribe_radius, ally_attack, ally_defence);
    //     this.ally_defence = ally_defence;
    //     this.isMeet = isMeet;
    // }
    public MercenaryAlly(MercenaryEnemy mercenary) {
        super("mercenary", mercenary.getLocation(), mercenary.getAttack().attackDamage(), mercenary.getHealth(), 
                mercenary.getBribe_amount(), mercenary.getBribe_radius(), mercenary.getAlly_attack(), mercenary.getAlly_defence());
        isFollowing = false;
        effecs = false;
    // System.out.println("");
        // isMeet = false;
    }
    // public void setMeet(){
    //     this.isMeet = true;
    // }
    
    @Override
    public boolean movement(DungeonMap dungeonMap) {
        // super.movement(dungeonMap);
        System.out.println("Ally MOveing");
        Player p = dungeonMap.getPlayer();
        String options = MovingEntity.getPossibleNextDirection(dungeonMap, this);
        if (p.getLocation().equals(getLocation())) {
            setMove(new FollowingMovement(p.getPreviousLocation()));
        } 
        Location next = getMove().MoveOptions(options).nextLocation(p.getLocation());
        if (p.getLocation().equals(next)) {
            setMove(new FollowingMovement(p.getPreviousLocation()));
        } 
        System.out.println(String.format("Movement: Mercenary %s -> %s", getLocation(), next));

        setLocation(next);
        
        dungeonMap.UpdateEntity(this);
        // if (p.hasEffect()) {
        //     String options = "";
        //     if (p.getCurrentEffect().applyEffect().equals("Invisibility")) {
        //         setMove(new RandomMovement());
        //         options = MovingEntity.getPossibleNextDirection(dungeonMap, this);
        //     }
        //     isFollowing = false;
        //     setLocation(getMove().MoveOptions(options).nextLocation(p.getLocation()));
            
        // } else {
        //         // Player reach mercenary
        //         if (getLocation().equals(p.getLocation())) {
        //             isFollowing = true;
        //             setMove(new FollowingMovement(p.getPreviousLocation()));
        //         } else if (!isFollowing) {
        //             setMove(new ChaseMovement(getLocation()));
        //             setLocation(getMove().nextLocation(p.getLocation()));
        //             // mercenary reach Player
        //             if (getLocation().equals(p.getLocation())) {
        //                 isFollowing = true;
        //                 setMove(new FollowingMovement(p.getPreviousLocation()));
        //                 return true;
        //             }
        //         }
        //         if( isFollowing) {
        //             setLocation(getMove().nextLocation(p.getLocation()));
        //         }
        //     }

            // } else {

            // }
            // if (getLocation().equals(p.getLocation()) && isFollowing) {
            //     isFollowing = true;
            //     setMove(new FollowingMovement(p.getPreviousLocation()));
            // } else {
            //     setMove(new ChaseMovement(getLocation()));
            //     setLocation(getMove().MoveOptions(options).nextLocation(p.getLocation()));
            // }
        // // if (g)
        // Location playerLocation = p.getLocation();
        // // System.out.println("playerLocation:"+playerLocation);
        // // System.out.println("MercenaryLocation:"+this.getLocation());
        // if (p.getPreviousLocation().equals(getLocation())) {
        //     setMeet();
        //     super.changeStrategy(new FollowingMovement());
        //     return false;
        // }
        // if (isMeet) {
        //     changeStrategy(new FollowingMovement());
        //     Location playerPreLocation = p.getPreviousLocation();
        //     next = super.getMove().nextLocation(playerPreLocation);
        // } else {
        //     next = getMove().nextLocation(playerLocation);
        //     if (dungeonMap.checkMovement(next)) {
        //         next = getMove().moveWithWall(next, dungeonMap);
        //         if (next.equals(getLocation())) {
        //             return false;
        //         }
        //     }
        // }
        // setLocation(next);
        // dungeonMap.UpdateEntity(this);
        return true;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        return true;
    }

    @Override
    public double damage() {
        // TODO Auto-generated method stub
        return ally_attack;
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

    @Override
    public void update(PotionEffectSubject subject) {
        // TODO Auto-generated method stub
        if (subject instanceof Player) {
            update((Player) subject);
        }
        
    }
    public void update(Player player) {
        if (player.hasEffect() && player.getCurrentEffect().applyEffect().equals("Invisibility")) {
            setMove(new RandomMovement());
        } 
        if (!player.hasEffect()) {
            if (player.getLocation().equals(getLocation())) {
                setMove(new FollowingMovement(player.getPreviousLocation()));
            } else {
                setMove(new ChaseMovement(getLocation()));
            }
        }
    }
}
