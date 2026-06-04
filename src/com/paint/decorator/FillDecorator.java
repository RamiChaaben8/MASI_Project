package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FillDecorator extends ShapeDecorator {
    private Color fillColor;

    public FillDecorator(Shape s, Color fillColor) {
        super(s);
        this.fillColor = fillColor;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Color oldFill = (Color) gc.getFill();
        gc.setFill(fillColor);
        if (wrapped != null) {
            // Basic fill logic for demonstration
            gc.fillRect(wrapped.getX(), wrapped.getY(), wrapped.getW(), wrapped.getH()); 
        }
        super.draw(gc); // delegates to wrapped.draw(gc)
        gc.setFill(oldFill);
    }
}
