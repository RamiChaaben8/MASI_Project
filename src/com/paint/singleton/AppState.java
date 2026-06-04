package com.paint.singleton;

import com.paint.observer.IObserver;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static AppState instance;
    private List<IObserver> observers = new ArrayList<>();
    
    private Color currentColor = Color.BLACK;
    private double currentLineWidth = 2.0;

    private AppState() { }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void registerObserver(IObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void unregisterObserver(IObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update();
        }
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor = currentColor;
        notifyObservers();
    }

    public double getCurrentLineWidth() {
        return currentLineWidth;
    }

    public void setCurrentLineWidth(double currentLineWidth) {
        this.currentLineWidth = currentLineWidth;
        notifyObservers();
    }
}