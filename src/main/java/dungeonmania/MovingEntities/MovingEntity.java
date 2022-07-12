package dungeonmania.MovingEntities;

import dungeonmania.Entity;
import dungeonmania.Strategies.AttackStrategies.AttackStrayegy;

public abstract class MovingEntity extends Entity {
    private AttackStrayegy attack;

    public MovingEntity(AttackStrayegy attack) {
        this.attack = attack;
    }
    public AttackStrayegy getAttack() {
        return attack;
    }
}
