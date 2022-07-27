package dungeonmania.staticEntities;

import dungeonmania.Entity;
import dungeonmania.Interact;
import dungeonmania.Player;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.helpers.Location;
import dungeonmania.movingEntities.ZombieToast;

public class ZombieToastSpawner extends StaticEntity implements Interact {
    private int timer;
    private int zombieSpawnRate;
    private int zombie_attack;
    private int zombie_health;
    private DungeonMap map;
    private int counter;

    public ZombieToastSpawner(String type, int x, int y, int zombieSpawnRate, int zombie_attack, int zombie_health,
            DungeonMap map) {
        super(type, Location.AsLocation(x, y));
        this.zombieSpawnRate = zombieSpawnRate;
        this.timer = 0;
        this.zombie_attack = zombie_attack;
        this.zombie_health = zombie_health;
        this.map = map;
        counter = 0;
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
            if (getLocation().getFourNearPosition().stream()
                    .allMatch(e -> (!CheckOpenSpace(e.apply(getLocation()).getX(), e.apply(getLocation()).getY())))) {
                return;
            } else {
                ZombieToast zombie = new ZombieToast("zombie_toast", getLocation().clone(), zombie_attack,
                        zombie_health);
                zombie.setEntityId(String.format("%s_%s_%s_%d", "zombie_toast", getType(), getLocation().toString(), counter));
                zombie.movement(map);
                map.UpdateEntity(zombie);
                System.out.println("PUT ZOMBIE TO MAP");
                counter++;
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
        return true;
    }

    @Override
    public boolean interact(Player player, DungeonMap dungeonMap) {
        if (!dungeonMap.getFourNearEntities(getLocation()).contains(player)) {
            return false;
        }
        if (player.getInventory().hasWeapons()) {
            dungeonMap.removeEntity(getEntityId());
            return true;
        }
        return false;
    }
}
