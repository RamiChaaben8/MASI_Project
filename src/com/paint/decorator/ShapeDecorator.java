package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeDecorator extends Shape {
    protected Shape wrapped;

    public ShapeDecorator(Shape s) {
        super(s != null ? s.getColor() : null);
        this.wrapped = s;
        if (s != null) {
            this.x = s.getX();
            this.y = s.getY();
            this.w = s.getW();
            this.h = s.getH();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (wrapped != null) {
            wrapped.draw(gc);
        }
    }
    
    @Override
    public Shape clone() {
        ShapeDecorator clone = (ShapeDecorator) super.clone();
        if (this.wrapped != null) {
            clone.wrapped = this.wrapped.clone();
        }
        return clone;
    }
}
