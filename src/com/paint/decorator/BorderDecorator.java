package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BorderDecorator extends ShapeDecorator {
    private transient Color strokeColor;
    private double strokeWidth;

    public BorderDecorator(Shape s, Color strokeColor, double strokeWidth) {
        super(s);
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject();
        if (strokeColor != null) {
            out.writeBoolean(true);
            out.writeDouble(strokeColor.getRed());
            out.writeDouble(strokeColor.getGreen());
            out.writeDouble(strokeColor.getBlue());
            out.writeDouble(strokeColor.getOpacity());
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
            this.strokeColor = new Color(r, g, b, a);
        }
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
