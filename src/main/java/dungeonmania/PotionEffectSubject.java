package dungeonmania;

public interface PotionEffectSubject {
    public void attach(PotionEffecObserver observer);
    public void detach(PotionEffecObserver observer);
    public void notifyObserver();
}
