package communication;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Observable<E> {
    private HashSet<Observer<E>> observers = new HashSet<>();

    public void addObserver(Observer<E> observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer<E> observer) {
        observers.remove(observer);
    }

    public void removeAllObservers() {
        observers.clear();
    }

    public void notifyObservers(E obj) {
        for(Observer<E> observer : observers) {
            observer.update(obj);
        }
    }
    public HashSet<Observer<E>> getObservers(){
        return observers;
    }
}
