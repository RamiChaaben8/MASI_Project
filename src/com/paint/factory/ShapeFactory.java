package com.paint.factory;

import javafx.scene.paint.Color;

public class ShapeFactory {

    private IShapeFactory factory;

    public ShapeFactory(IShapeFactory factory) {
        this.factory = factory;
    }

    public void setFactory(IShapeFactory factory) {
        this.factory = factory;
    }

    public Shape createShape(Color color) {
        return factory.createShape(color);
    }
}
