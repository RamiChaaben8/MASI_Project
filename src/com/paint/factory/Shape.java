package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.Serializable;

public abstract class Shape implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    protected transient Color color;
    protected double x, y;
    protected double w, h;

    public Shape(Color color) {
        this.color = color;
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setW(double w) { this.w = w; }
    public void setH(double h) { this.h = h; }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getW() { return w; }
    public double getH() { return h; }
    public Color getColor() { return color; }

    public abstract void draw(GraphicsContext gc);

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject();
        if (color != null) {
            out.writeBoolean(true);
            out.writeDouble(color.getRed());
            out.writeDouble(color.getGreen());
            out.writeDouble(color.getBlue());
            out.writeDouble(color.getOpacity());
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
            this.color = new Color(r, g, b, a);
        }
    }
}
