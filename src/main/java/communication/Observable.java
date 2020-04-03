package communication;

import java.util.ArrayList;
import java.util.List;

public class Observable<E> {
    private List<Observer<E>> observers = new ArrayList<>();

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
    public List<Observer<E>> getObservers(){
        return observers;
    }
}
