package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;

public interface DrawStrategy {
    void onPress(double x, double y);
    void onDrag(double x, double y);
    void onRelease(double x, double y);
}
