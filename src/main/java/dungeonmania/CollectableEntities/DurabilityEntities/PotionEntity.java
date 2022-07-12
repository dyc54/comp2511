package dungeonmania.CollectableEntities.DurabilityEntities;

public abstract class PotionEntity extends DurabilityEntity{
    private String Effect;
    private boolean inUsing = false;

    public PotionEntity(String type, int durability, int x , int y, String Effect) {
        super(type, durability, x, y);
        this.Effect = Effect;
    }

    public void setEffect(String effect) {
        Effect = effect;
    }

    public String getEffect() {
        if (inUsing) {
            return Effect;
        } else {
            return null;
        }
    }

    public void setInUsing() {
        inUsing = true;
    }
    
}
