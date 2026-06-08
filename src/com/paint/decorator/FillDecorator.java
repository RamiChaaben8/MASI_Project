package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FillDecorator extends ShapeDecorator {
    private transient Color fillColor;

    public FillDecorator(Shape s, Color fillColor) {
        super(s);
        this.fillColor = fillColor;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject();
        if (fillColor != null) {
            out.writeBoolean(true);
            out.writeDouble(fillColor.getRed());
            out.writeDouble(fillColor.getGreen());
            out.writeDouble(fillColor.getBlue());
            out.writeDouble(fillColor.getOpacity());
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
            this.fillColor = new Color(r, g, b, a);
        }
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
