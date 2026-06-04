package com.paint.singleton;

import com.paint.observer.IObserver;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static AppState instance;
    private Color currentColor = Color.BLACK;
    private int brushSize = 2;
    private List<IObserver> observers = new ArrayList<>();

    private AppState() { }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void setColor(Color c) {
        this.currentColor = c;
        notifyObservers();
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        notifyObservers();
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void addObserver(IObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void notifyObservers() {
        for (IObserver o : observers) {
            o.update();
        }
    }
}
