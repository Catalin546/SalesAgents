package com.example.salesagents.Domain;

import java.util.ArrayList;
import java.util.List;


public class IObservable<T> {
    private List<IObserver<T>> observers = new ArrayList<>();

    public void addObserver(IObserver<T> observer) {
        observers.add(observer);
    }

    public void notifyAllObservers(T data) {
        observers.forEach(observer -> observer.updateObserver(data));
    }
}