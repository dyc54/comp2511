package dungeonmania.StaticEntities;

import java.util.Random;

import dungeonmania.Entity;
import dungeonmania.EntityFactory;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.MovingEntities.ZombieToast;
import dungeonmania.helpers.Config;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;

public class ZombieToastSpawner extends StaticEntity implements Interact {
    private int timer;
    private int zombieSpawnRate;
    private int zombie_attack;
    private int zombie_health;
    private DungeonMap map;

    public ZombieToastSpawner(String type, int x, int y, int zombieSpawnRate, int zombie_attack, int zombie_health,
            DungeonMap map) {
        super(type, Location.AsLocation(x, y));
        this.zombieSpawnRate = zombieSpawnRate;
        this.timer = 0;
        this.zombie_attack = zombie_attack;
        this.zombie_health = zombie_health;
        this.map = map;
    }

    public int getZombieSpawnRate() {
        return this.zombieSpawnRate;
    }

    public int getTimer() {
        return this.timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public void TimerAdd() {
        this.timer += 1;
    }

    public void ZombieToastSpwanCheck() {
        TimerAdd();
        if (timer == zombieSpawnRate) {
            // If there are obstacles at all four cardinally adjacent position, don't spwan
            if (getLocation().getFourNearPosition().stream()
                    .allMatch(e -> (!CheckOpenSpace(e.apply(getLocation()).getX(), e.apply(getLocation()).getY())))) {
                return;
            } else {
                // spawn
                ZombieToast zombie = new ZombieToast("zombie_toast", getLocation(), zombie_attack, zombie_health);
                zombie.movement(map);
                System.out.println("after:" + zombie.getLocation());
                setTimer(0);
            }
        }
    }

    public boolean CheckOpenSpace(int x, int y) {

        if (map.getEntities(x, y).stream().anyMatch(e -> e.getType().equals("door"))
                || map.getEntities(x, y).stream().anyMatch(e -> e.getType().equals("boulder"))
                || map.getEntities(x, y).stream().anyMatch(e -> e.getType().equals("wall"))) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isAccessible(Entity entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        System.out.println(getLocation());
        System.out.println(player.getLocation());
        if (!dungeonMap.getFourNearEntities(getLocation()).contains(player)) {
            System.out.println("\\\\\\");
            return false;
        }
        player.getInventory();
        if (player.getInventory().hasWeapons()) {
            dungeonMap.removeEntity(getEntityId());
            return true;
        }
        return false;
    }
}
