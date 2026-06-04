package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleShape extends Shape {
    public CircleShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double radius = Math.max(Math.abs(endX - startX), Math.abs(endY - startY));
        gc.setStroke(color);
        gc.strokeOval(startX - radius, startY - radius, radius * 2, radius * 2);
    }
}