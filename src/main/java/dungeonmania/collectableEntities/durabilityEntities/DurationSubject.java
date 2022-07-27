package dungeonmania.collectableEntities.durabilityEntities;

public interface DurationSubject {
    public void attach(DurationObserver observer);
    public void detach(DurationObserver observer);
    public void notifyDurationObserver();
}
