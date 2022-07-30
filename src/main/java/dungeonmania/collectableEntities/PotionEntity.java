package dungeonmania.collectableEntities;

import dungeonmania.Durability;
import dungeonmania.Player;
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
        setInUsing();
        player.getInventory().removeFromInventoryList(this);
    }

    @Override
    public void setDurability() {
        if (inUsing) {
            this.durability -= 1;
        }
    }

    @Override
    public boolean checkDurability(){
        
        return durability == 0;
    }
}
