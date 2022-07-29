/* 
package dungeonmania.collectableEntities.durabilityEntities;

import java.util.ArrayList;

public class Durability implements DurationSubject {
    ArrayList<DurationObserver> observers = new ArrayList<>();
    // @Override
    public void refrashDuration() {
        
    }
    @Override
    public void attach(DurationObserver observer) {
        // TODO Auto-generated method stub
        observers.add(observer);
        
    }

    @Override
    public void detach(DurationObserver observer) {
        // TODO Auto-generated method stub
        observers.remove(observer);
    }

    @Override
    public void notifyDurationObserver() {
        // TODO Auto-generated method stub
        for (DurationObserver observer: observers) {
            observer.update(this);
        }
        
    }
    
}
*/