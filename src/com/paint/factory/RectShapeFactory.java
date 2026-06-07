package com.paint.factory;

import javafx.scene.paint.Color;

public class RectShapeFactory implements IShapeFactory{
    public Shape createShape(Color color){
        return new RectShape(color);
    }
}
