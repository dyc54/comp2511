package dungeonmania.buildableEntities;

import dungeonmania.Player;
import dungeonmania.collectableEntities.Effect;
import dungeonmania.collectableEntities.Useable;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.movingEntities.Mercenary;
import dungeonmania.movingEntities.MercenaryAlly;


public class Sceptre extends BuildableEntity implements Useable, Effect{

    private int Duration;
    private int timer;
    private boolean timerStart;

    public Sceptre(String type, int Duration, String id) {
        super(type, id);
        this.Duration = Duration;
        this.timer = 0;
        this.timerStart = false;
    }

    public boolean checkTimer() {
        if (timer == Duration) {
            return true;
        } else {
            return false;
        }
    }

    public void setTimer() {
        this.timer += 1;
    }

    public int getTimer() {
        return this.timer;
    }

    public void StartTimer() {
        this.timerStart = true;
        this.timer = 0;
    }

    public boolean getTimerStart() {
        return this.timerStart;
    }

    public void stopTimer() {
        this.timerStart = false;
        this.timer = 0;
    }

    @Override
    public void use(DungeonMap map, Player player) {
        StartTimer();
        map.getAllEntities().stream().filter(e -> e instanceof Mercenary).forEach(e-> {
            Mercenary enemy = (Mercenary) e;
            MercenaryAlly ally = new MercenaryAlly(enemy);
            ally.setEntityId(String.valueOf(enemy.getEntityId()));
            map.removeEntity(enemy.getEntityId());
            map.addEntity(ally);
            player.addsceptreObservers(ally);
            player.getAttackStrategy().bonusDamage(ally);
            player.getDefenceStrayegy().bonusDefence(ally);
            player.attach(ally);
        });
    }

    @Override
    public String applyEffect() {
        return null;
    }
    
}
