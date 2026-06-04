package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class ShadowDecorator extends ShapeDecorator {
    private Color shadowColor;
    private int offset;

    public ShadowDecorator(Shape s, Color shadowColor, int offset) {
        super(s);
        this.shadowColor = shadowColor;
        this.offset = offset;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        DropShadow ds = new DropShadow();
        ds.setOffsetY(offset);
        ds.setOffsetX(offset);
        ds.setColor(shadowColor);
        gc.setEffect(ds);
        
        super.draw(gc);
        
        gc.restore();
    }
}
