package dungeonmania.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.CollectableEntity;
import dungeonmania.collectableEntities.durabilityEntities.buildableEntities.MidnightArmour;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
import dungeonmania.strategies.battleStrategies.BattleStrategyWithEnemy;
import dungeonmania.strategies.battleStrategies.BattleStrategyWithPlayer;
import dungeonmania.strategies.defenceStrategies.DefenceStrategy;

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
    // private double playerDamage() {
    //     AttackStrategy attackStrayegy = player.getAttackStrategy();
    //     return attackStrayegy.attackDamage() / 5.0;
    // }
    // private double enemyDamage() {
    //     AttackStrategy attackStrayegy = enemy.getAttackStrayegy();
    //     DefenceStrategy defenceStrategy = player.getDefenceStrayegy();
    //     double damage = attackStrayegy.attackDamage() - defenceStrategy.defenceDamage();
    //     return (damage > 0 ? damage : 0) / 10.0;
    // }
    // private int increaseAmount() {
    //     AttackStrategy attackStrategy = enemy.getAttackStrayegy();
    //     return attackStrategy.getIncreaseAmount();
    // }
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
        System.out.println(String.format("Set Battle: \nPlayer HP:%f\nEnemy %s, HP:%f", initPlayerHealth, enemy.getEnemyType(), initEnemyHealth));
        return this;
    }
    
    /**
     * 
     * @return
     */
    private List<String> battle() {
        BattleStrategyWithPlayer enemy = (BattleStrategyWithPlayer) this.enemy;
        BattleStrategyWithEnemy player = (BattleStrategyWithEnemy) this.player;
        double deltaEnemy = enemy.battleDamageFrom(this.player);
        double deletePlayer = player.battleDamageFrom(this.enemy);
        
        System.out.println(String.format("Round P:%f - %f=%f\nE:%f - %f=%f", currPlayerHealth, deletePlayer, currPlayerHealth - deletePlayer
                                                                                        , currEnemyHealth, deltaEnemy, currEnemyHealth - deltaEnemy));
        
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
        List<String> removed_ids = battle();
        player.setBattleUsedDuration();
        if (removed_ids.size() > 0) {
            return removed_ids;
        } 
        System.out.println(String.format("Current Battle effect : (%s)", effect));
        if (effect.equals("Invincibility")) {
            return new ArrayList<>();
        }
        while (removed_ids.size() == 0) {
            removed_ids = battle();
        }
        return removed_ids;

    }
    public BattleResponse toResponse() {
        return new BattleResponse(enemy.getEnemyType(), rounds, initPlayerHealth, initEnemyHealth);
    }
}
