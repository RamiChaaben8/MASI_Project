package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;

public interface DrawStrategy {
    void draw(GraphicsContext gc, double x, double y);
}
