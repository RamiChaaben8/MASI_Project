package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface DrawStrategy {
    void draw(MouseEvent event, GraphicsContext gc);
}