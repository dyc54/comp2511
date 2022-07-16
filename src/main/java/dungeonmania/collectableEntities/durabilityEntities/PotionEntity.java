package dungeonmania.collectableEntities.durabilityEntities;

import dungeonmania.collectableEntities.Effect;

public abstract class PotionEntity extends DurabilityEntity implements Effect {
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
    
}
