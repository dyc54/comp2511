package dungeonmania.CollectableEntities.DurabilityEntities;

public abstract class PotionEntity extends DurabilityEntity{
    private String Effect;
    private boolean inUsing = false;

    public PotionEntity(String id, String type, int durability,int x , int y) {
        super(id, type, durability);
        setLocation(x, y);
        
    }

    public void setEffect(String effect) {
        Effect = effect;
    }

    public String getEffect() {
        if(inUsing){
            return Effect;
        }else{
            return null;
        }
    }

    public void setInUsing() {
        inUsing = true;
    }
    
}
