package BusinessLayer.Units;

import BusinessLayer.Observer;

import java.util.LinkedList;
import java.util.List;

public abstract class Observable {
    private List<Observer> observers = new LinkedList<>();

    public void register(Observer o){
        observers.add(o);
    }
    public void notifyObserver(String msg){
        for (Observer o : observers)
            o.onEvent(msg);
    }
}
