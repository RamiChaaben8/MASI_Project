package com.paint.factory;

import javafx.scene.paint.Color;

public interface IShapeFactory {
    public Shape createShape(Color color);
}
