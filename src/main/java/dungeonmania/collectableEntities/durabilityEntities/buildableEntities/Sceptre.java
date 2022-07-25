package dungeonmania.collectableEntities.durabilityEntities.buildableEntities;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.Useable;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.MercenaryAlly;
import dungeonmania.movingEntities.MercenaryEnemy;

public class Sceptre extends Entity implements Useable{

    private int Duration;
    private int round;
    private boolean roundStart;

    public Sceptre(String type, int Duration, String id) {
        super(type);
        this.Duration = Duration;
        this.round = 0;
        this.roundStart = false;
    }

    public boolean checkRound() {
        if (round == Duration) {
            return true;
        } else {
            return false;
        }
    }

    public void setRound() {
        this.round = round += 1;
    }

    public void StartRound() {
        this.roundStart = true;
        this.round = 0;
    }

    public boolean getRoundStart() {
        return this.roundStart;
    }

    public void stopRound() {
        this.roundStart = false;
        this.round = 0;
    }

    @Override
    public void use(DungeonMap map, Player player) {
        StartRound();
        map.getEntities("mercenary").forEach(e-> {
            MercenaryEnemy enemy = (MercenaryEnemy) e;
            MercenaryAlly ally = new MercenaryAlly(enemy);
            ally.setEntityId(String.valueOf(enemy.getEntityId()));
            map.removeEntity(enemy.getEntityId());
            map.addEntity(ally);
            player.getAttackStrategy().bonusDamage(ally);
            player.getDefenceStrayegy().bonusDefence(ally);
            player.attach(ally);
        });
    }
    

    
}
