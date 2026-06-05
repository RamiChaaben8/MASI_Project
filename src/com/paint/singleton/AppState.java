package com.paint.singleton;

import com.paint.observer.IObserver;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static AppState instance;
    private Color currentColor = Color.BLACK;
    private Color secondaryColor = Color.RED;
    private int brushSize = 2;
    private boolean useFill = false;
    private boolean useBorder = false;
    private boolean useShadow = false;
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

    public void setSecondaryColor(Color c) {
        this.secondaryColor = c;
        notifyObservers();
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        notifyObservers();
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void setUseFill(boolean useFill) {
        this.useFill = useFill;
        notifyObservers();
    }

    public boolean isUseFill() { return useFill; }

    public void setUseBorder(boolean useBorder) {
        this.useBorder = useBorder;
        notifyObservers();
    }

    public boolean isUseBorder() { return useBorder; }

    public void setUseShadow(boolean useShadow) {
        this.useShadow = useShadow;
        notifyObservers();
    }

    public boolean isUseShadow() { return useShadow; }

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
