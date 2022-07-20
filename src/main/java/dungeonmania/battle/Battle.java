package dungeonmania.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.collectableEntities.CollectableEntity;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.ItemResponse;
import dungeonmania.response.models.RoundResponse;
import dungeonmania.strategies.attackStrategies.AttackStrategy;
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
    private double playerDamage() {
        AttackStrategy attackStrayegy = player.getAttackStrategy();
        return attackStrayegy.attackDamage() / 5.0;
    }
    private double enemyDamage() {
        AttackStrategy attackStrayegy = enemy.getAttackStrayegy();
        DefenceStrategy defenceStrategy = player.getDefenceStrayegy();
        double damage = attackStrayegy.attackDamage() - defenceStrategy.defenceDamage();
        return (damage > 0 ? damage : 0) / 10.0;
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
        System.out.println(String.format("Set Battle: \nPlayer HP:%f\nEnemy %s, HP:%f", initPlayerHealth, enemy.getEnemyType(), initEnemyHealth));
        return this;
    }
    private double round(double num) {
        if (num >= -0.0001 && num <= 0.0001) {
            return 0.0;
        }
        return num;
    }
    /**
     * 
     * @return
     */
    private List<String> battle() {
        double playerdamage = playerDamage();
        double enemydamage = enemyDamage();
        List<ItemResponse> items= player.getBattleUsage()
                                    .stream().map(mapper -> (CollectableEntity) mapper)
                                    .map(item -> item.getItemResponse()).collect(Collectors.toList());
        if (player.hasEffect()) {
            items.add(player.getCurrentEffect().getItemResponse());
        }               
        System.out.println(String.format("Round P:%f - %f=%f\nE:%f - %f=%f", currPlayerHealth, enemydamage, currPlayerHealth - enemydamage
                                                                                        , currEnemyHealth, playerdamage, currEnemyHealth - playerdamage));
        
        currPlayerHealth -= enemydamage;
        currEnemyHealth -= playerdamage;
        rounds.add(new RoundResponse(enemydamage * -1, playerdamage * -1, items));

        if (round(currPlayerHealth) <= 0 && round(currEnemyHealth) <= 0) {
            player.setHealth(0);
            return Arrays.asList(player.getEntityId(), enemy.getEnemyId());
        }
        if (round(currPlayerHealth) <= 0) {
            enemy.setHealth(currEnemyHealth);
            return Arrays.asList(player.getEntityId());
        } else if (round(currEnemyHealth) <= 0) {
            player.setHealth(round(currPlayerHealth));
            return Arrays.asList(enemy.getEnemyId());
        } else {
            player.setHealth(round(currPlayerHealth));
            enemy.setHealth(currEnemyHealth);
            return new ArrayList<>();
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