package dungeonmania.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dungeonmania.Player;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.strategies.battleStrategies.BattleStrategyWithEnemy;
import dungeonmania.strategies.battleStrategies.BattleStrategyWithPlayer;

public class Battle {
    private Player player;
    private Enemy enemy;
    private double initPlayerHealth;
    private double initEnemyHealth;
    private double currPlayerHealth;
    private double currEnemyHealth;
    private List<RoundResponse> rounds;
    public Battle() {
        rounds = new ArrayList<>();

    }
    
    public Battle(Player player, Enemy enemy) {
        this();
        this.player = player;
        this.enemy = enemy;
        this.initPlayerHealth = player.getHealth();
        this.initEnemyHealth = enemy.getHealth();

    }
    /**
     * Start a Battle
     * @param player
     * @param enemy
     * @return
     */
    public Battle setBattle(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.initPlayerHealth = player.getHealth();
        this.initEnemyHealth = enemy.getHealth();
        currPlayerHealth = initPlayerHealth;
        currEnemyHealth = initEnemyHealth;
        return this;
    }
    
    /**
     * 
     * @return
     */
    private List<String> battle() {
        currPlayerHealth = player.getHealth();
        currEnemyHealth = enemy.getHealth();
        BattleStrategyWithPlayer enemy = (BattleStrategyWithPlayer) this.enemy;
        BattleStrategyWithEnemy player = (BattleStrategyWithEnemy) this.player;
        double deltaEnemy = enemy.battleDamageFrom(this.player);
        double deletePlayer = player.battleDamageFrom(this.enemy);
        rounds.add(new RoundResponse(deletePlayer * -1, deltaEnemy * -1, player.getBattleUsedItems()));
        player.battleWith(this.enemy);
        enemy.battleWith(this.player);
        if (player.isAlive() && enemy.isAlive()) {
            return Arrays.asList();
        }
        if (player.isAlive()) {
            return Arrays.asList(this.enemy.getEnemyId());
        } else if (enemy.isAlive()) {
            return Arrays.asList(this.player.getEntityId());
        } else {
            return Arrays.asList(this.player.getEntityId(), this.enemy.getEnemyId());
        }
    }
    /**
     * Start a battle
     * @return
     */
    public List<String> startBattle() {
        String effect = "";
        if (player.hasEffect()) {
            effect = player.getCurrentEffect().applyEffect();
        }
        List<String> removedIds = battle();
        player.setBattleUsedDuration();
        if (removedIds.size() > 0) {
            return removedIds;
        } 
        if (effect.equals("Invincibility")) {
            return new ArrayList<>();
        }
        while (removedIds.size() == 0) {
            removedIds = battle();
        }
        return removedIds;

    }
    public BattleResponse toResponse() {
        return new BattleResponse(enemy.getEnemyType(), rounds, initPlayerHealth, initEnemyHealth);
    }
}
