package com.paint.factory;

import javafx.scene.paint.Color;

public class CircleShapeFactory implements  IShapeFactory{
    public Shape createShape(Color color){
        return  new CircleShape(color);
    }
}
