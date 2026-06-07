package com.paint.factory;

import javafx.scene.paint.Color;

public class LineShapeFactory implements IShapeFactory{
    public Shape createShape(Color color){
        return new LineShape(color);
    }
}
