package com.paint.strategy;

import com.paint.factory.ShapeFactory;
import com.paint.factory.Shape;
import com.paint.singleton.AppState;
import com.paint.decorator.BorderDecorator;
import com.paint.decorator.FillDecorator;
import com.paint.decorator.ShadowDecorator;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class ShapeStrategy implements DrawStrategy {
    private String shapeType;
    private double startX, startY;

    public ShapeStrategy(String shapeType) {
        this.shapeType = shapeType;
    }

    @Override
    public void draw(MouseEvent event, GraphicsContext gc) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            startX = event.getX();
            startY = event.getY();
        } else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
            Shape baseShape = ShapeFactory.createShape(shapeType, AppState.getInstance().getCurrentColor());
            baseShape.setBounds(startX, startY, event.getX(), event.getY());
            
            // Applying Decorator chain
            Shape decoratedShape = new ShadowDecorator(new BorderDecorator(new FillDecorator(baseShape)));
            decoratedShape.draw(gc);
        }
    }
}