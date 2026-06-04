package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected double startX, startY, endX, endY;
    protected Color color;

    public Shape(Color color) {
        this.color = color;
    }

    public void setBounds(double startX, double startY, double endX, double endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public abstract void draw(GraphicsContext gc);
}