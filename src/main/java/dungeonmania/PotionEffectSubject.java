package dungeonmania;

public interface PotionEffectSubject {
    public void attach(PotionEffectObserver observer);
    public void detach(PotionEffectObserver observer);
    public void notifyObserver();
}
