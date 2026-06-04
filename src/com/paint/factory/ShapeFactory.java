package com.paint.factory;

import javafx.scene.paint.Color;

public class ShapeFactory {
    public static Shape createShape(String type, Color color) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("CIRCLE")) {
            return new CircleShape(color);
        } else if (type.equalsIgnoreCase("RECTANGLE")) {
            return new RectShape(color);
        } else if (type.equalsIgnoreCase("LINE")) {
            return new LineShape(color);
        }
        return null;
    }
}