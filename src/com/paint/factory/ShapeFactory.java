package com.paint.factory;

import javafx.scene.paint.Color;

public class ShapeFactory {

    public Shape createShape(String type, Color color) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("CIRCLE")) {
            return new CircleShape(color);
        } else if (type.equalsIgnoreCase("RECT")) {
            return new RectShape(color);
        } else if (type.equalsIgnoreCase("LINE")) {
            return new LineShape(color);
        }
        return null;
    }
    /*
    public IShapeFactory factory;
    public ShapeFactory(IShapeFactory factory){
        this.factory = factory;
    }

    public void setFactory(IShapeFactory factory) {
        this.factory = factory;
    }
    public Shape createShape(Color color){
        return factory.createShape(color);
    }
    */

}
