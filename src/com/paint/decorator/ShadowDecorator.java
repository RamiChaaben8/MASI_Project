package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class ShadowDecorator extends ShapeDecorator {
    private transient Color shadowColor;
    private int offset;

    public ShadowDecorator(Shape s, Color shadowColor, int offset) {
        super(s);
        this.shadowColor = shadowColor;
        this.offset = offset;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject();
        if (shadowColor != null) {
            out.writeBoolean(true);
            out.writeDouble(shadowColor.getRed());
            out.writeDouble(shadowColor.getGreen());
            out.writeDouble(shadowColor.getBlue());
            out.writeDouble(shadowColor.getOpacity());
        } else {
            out.writeBoolean(false);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (in.readBoolean()) {
            double r = in.readDouble();
            double g = in.readDouble();
            double b = in.readDouble();
            double a = in.readDouble();
            this.shadowColor = new Color(r, g, b, a);
        }
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
