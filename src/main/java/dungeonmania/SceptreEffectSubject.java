package dungeonmania;

public interface SceptreEffectSubject {
    public void SceptreAttach(SceptreEffectObserver observer);
    public void SceptreDetach(SceptreEffectObserver observer);
    public void notifySceptreEffectObserver();
}
