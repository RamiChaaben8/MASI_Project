package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeDecorator extends Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        super(null); 
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void setBounds(double startX, double startY, double endX, double endY) {
        decoratedShape.setBounds(startX, startY, endX, endY);
    }

    @Override
    public void draw(GraphicsContext gc) {
        decoratedShape.draw(gc);
    }
}