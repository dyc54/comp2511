package dungeonmania.battle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
        // return 0;
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
        // System.out.println();
        return num;
    }
    private List<String> battle() {
        double playerdamage = playerDamage();
        double enemydamage = enemyDamage();
        List<ItemResponse> items= player.getBattleUsage()
                                    .stream().map(mapper -> (CollectableEntity) mapper)
                                    .map(item -> item.getItemResponse()).collect(Collectors.toList());
                                  
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
        // System.out.println("Current");
        System.out.println(String.format("Current Battle effect : (%s)", effect));
        if (effect.equals("Invincibility")) {
            return new ArrayList<>();
        }
        while (removed_ids.size() == 0) {
            removed_ids = battle();
        }
        return removed_ids;




        // System.out.println(String.format("effect %s", effect));
        // double currPlayerHealth = initPlayerHealth;
        // double currEnemyHealth = initEnemyHealth;
        // double playerdamage = playerDamage();;
        // double enemydamage = enemyDamage();
        // List<ItemResponse> items= player.getBattleUsage()
        //                             .stream().map(mapper -> (CollectableEntity) mapper)
        //                             .map(item -> item.getItemResponse()).collect(Collectors.toList());
        // System.out.println(String.format("First Round P:%f - %f=%f\nE%f - %f=%f", currPlayerHealth, enemydamage, currPlayerHealth - enemydamage
        //                                                                         , currEnemyHealth, playerdamage, currEnemyHealth - playerdamage));
        // currPlayerHealth -= enemydamage;
        // currEnemyHealth -= playerdamage;
        // rounds.add(new RoundResponse(enemydamage * -1, playerdamage * -1, items));
        
        // if (currPlayerHealth > 0 && effect.equals("Invincibility")) {
        //     return "null";
        //     // enemy.get
        // }
        // // TODO: IF invincibility activity
        // while (currPlayerHealth > 0 && currEnemyHealth > 0) {
        //     playerdamage = playerDamage();
        //     enemydamage = enemyDamage();
        //     System.out.println(String.format("Round P:%f - %f=%f\nE:%f - %f=%f", currPlayerHealth, enemydamage, currPlayerHealth - enemydamage
        //                                     , currEnemyHealth, playerdamage, currEnemyHealth - playerdamage));
        //     currPlayerHealth -= enemydamage;
        //     currEnemyHealth -= playerdamage;
        //     // RoundResponse response = new RoundResponse(enemydamage * -1.0, playerdamage * -1.0, items);
        //     // System.out.println(response.getDeltaCharacterHealth());
        //     // rounds.add(new RoundResponse(enemydamage * -1.0, playerdamage * -1.0, items));
        //     rounds.add(new RoundResponse(enemydamage * -1, playerdamage * -1, items));
        // }
        // if (currPlayerHealth <= 0 && currEnemyHealth <= 0) {
        //     player.setHealth(0);
        //     return "Both";
        // }
        // if (currPlayerHealth <= 0) {
        //     return player.getEntityId();
        // } else {
        //     player.setHealth(currPlayerHealth);
        //     return enemy.getEnemyId();
        // }
    }
    public BattleResponse toResponse() {
        return new BattleResponse(enemy.getEnemyType(), rounds, initPlayerHealth, initEnemyHealth);
    }
}
