package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BorderDecorator extends ShapeDecorator {
    private Color strokeColor;
    private double strokeWidth;

    public BorderDecorator(Shape s, Color strokeColor, double strokeWidth) {
        super(s);
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Color oldStroke = (Color) gc.getStroke();
        double oldWidth = gc.getLineWidth();
        
        gc.setStroke(strokeColor);
        gc.setLineWidth(strokeWidth);
        
        super.draw(gc);
        
        gc.setStroke(oldStroke);
        gc.setLineWidth(oldWidth);
    }
}
