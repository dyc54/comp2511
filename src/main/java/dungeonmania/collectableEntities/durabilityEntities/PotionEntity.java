package dungeonmania.collectableEntities.durabilityEntities;

import dungeonmania.Durability;
import dungeonmania.Player;
import dungeonmania.collectableEntities.CollectableEntity;
import dungeonmania.collectableEntities.Effect;
import dungeonmania.collectableEntities.Useable;
import dungeonmania.helpers.DungeonMap;

public abstract class PotionEntity extends CollectableEntity implements Effect, Useable, Durability {
    private String effect;
    private boolean inUsing = false;
    private int durability;

    public PotionEntity(String type, int durability, int x , int y, String effect) {
        super(type, x, y);
        this.effect = effect;
        this.durability = durability;
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
        player.notifyPotionEffectObserver();
        player.getInventory().removeFromInventoryList(this);
    }

    @Override
    public void setDurability() {
        System.out.println(String.format("Item %s DUration %d -> %d", getEntityId(), durability, durability - 1));
        this.durability -= 1;
    }

    @Override
    public boolean checkDurability(){
        System.out.println(String.format("Item %s DUration %d ", getEntityId(), durability));
        
        return durability == 0;
    }
}
