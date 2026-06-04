package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FillDecorator extends ShapeDecorator {
    public FillDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw(GraphicsContext gc) {
        Color oldFill = (Color) gc.getFill();
        gc.setFill(Color.LIGHTBLUE); // Dummy fill logic for decorator example
        gc.setGlobalAlpha(0.3);
        super.draw(gc);
        gc.setGlobalAlpha(1.0);
        gc.setFill(oldFill);
    }
}