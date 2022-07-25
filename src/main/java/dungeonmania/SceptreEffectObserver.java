package dungeonmania;

import dungeonmania.helpers.DungeonMap;

public interface SceptreEffectObserver {
    public void SceptreUpdate(SceptreEffectSubject subject, DungeonMap map);
}
