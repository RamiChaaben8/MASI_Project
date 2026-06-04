package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;

public class BorderDecorator extends ShapeDecorator {
    public BorderDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw(GraphicsContext gc) {
        double oldLineWidth = gc.getLineWidth();
        gc.setLineWidth(oldLineWidth + 2.0);
        super.draw(gc);
        gc.setLineWidth(oldLineWidth);
    }
}