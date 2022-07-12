package dungeonmania.Battle;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.Entity;
import dungeonmania.Player;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;
import dungeonmania.Strategies.DefenceStrategies.DefenceStrategy;
import dungeonmania.helpers.DungeonMap;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.RoundResponse;

public class Battle {
    private Player player;
    private Enemy enemy;
    private double initPlayerHealth;
    private double initEnemyHealth;
    private List<RoundResponse> rounds;
    public Battle() {
        rounds = new ArrayList<>();

    }
    private double playerDamage() {
        AttackStrayegy attackStrayegy = player.getAttackStrayegy();
        return attackStrayegy.attackDamage() / 5.0;
        // return 0;
    }
    private double enemyDamage() {
        AttackStrayegy attackStrayegy = enemy.getAttackStrayegy();
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
        return this;
    }
    public String startBattle() {
        double currPlayerHealth = initPlayerHealth;
        double currEnemyHealth = initEnemyHealth;
        double playerdamage = playerDamage();;
        double enemydamage = enemyDamage();
        currPlayerHealth -= enemydamage;
        currEnemyHealth -= playerdamage;
        rounds.add(new RoundResponse(enemydamage, playerdamage, new ArrayList<>()));
        // if ()
        // TODO: IF invincibility activity
        while (currPlayerHealth > 0 || currEnemyHealth > 0) {
            playerdamage = playerDamage();;
            enemydamage = enemyDamage();
            currPlayerHealth -= enemydamage;
            currEnemyHealth -= playerdamage;
            rounds.add(new RoundResponse(enemydamage, playerdamage, new ArrayList<>()));
        }
        if (currPlayerHealth <= 0 && currEnemyHealth <= 0) {
            return "Both";
        }
        if (currPlayerHealth <= 0) {
            return player.getEntityId();
        } else {
            player.setHealth(currPlayerHealth);
            return enemy.getEnemyId();
        }
    }
    public BattleResponse toResponse() {
        return null;
    }
}
