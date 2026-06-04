package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ShadowDecorator extends ShapeDecorator {
    public ShadowDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw(GraphicsContext gc) {
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(3.0);
        dropShadow.setOffsetY(3.0);
        dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
        
        gc.setEffect(dropShadow);
        super.draw(gc);
        gc.setEffect(null);
    }
}