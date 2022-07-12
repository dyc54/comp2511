package dungeonmania.StaticEntities;

public class ZombieToastSpawner extends StaticEntity {
    private int zombieSpawnRate;

    public ZombieToastSpawner(String type, int x, int y, int zombieSpawnRate) {
        super(type, x, y);
        this.zombieSpawnRate = zombieSpawnRate;
    }

    public int getZombieSpawnRate() {
        return this.zombieSpawnRate;
    }
}
