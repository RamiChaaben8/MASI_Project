package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectShape extends Shape {
    public RectShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double x = Math.min(startX, endX);
        double y = Math.min(startY, endY);
        double width = Math.abs(endX - startX);
        double height = Math.abs(endY - startY);
        gc.setStroke(color);
        gc.strokeRect(x, y, width, height);
    }
}