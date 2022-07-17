package dungeonmania.collectableEntities.durabilityEntities;

import dungeonmania.Player;
import dungeonmania.collectableEntities.Effect;
import dungeonmania.collectableEntities.Useable;
import dungeonmania.helpers.DungeonMap;

public abstract class PotionEntity extends DurabilityEntity implements Effect, Useable {
    private String effect;
    private boolean inUsing = false;

    public PotionEntity(String type, int durability, int x , int y, String effect) {
        super(type, durability, x, y);
        this.effect = effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getEffect() {
        if (inUsing) {
            return effect;
        } else {
            return null;
        }
    }

    public void setInUsing() {
        inUsing = true;
    }
    @Override
    public void use(DungeonMap map, Player player) {
        player.addeffect((PotionEntity) this);
        player.notifyObserver();
        player.getInventory().removeFromInventoryList(this);
    }
}
