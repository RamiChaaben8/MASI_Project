package com.paint.strategy;

import javafx.scene.paint.Color;
import com.paint.factory.IShapeFactory;
import com.paint.factory.ShapeFactory;
import com.paint.factory.Shape;
import com.paint.singleton.AppState;
import com.paint.observer.Canvas;
import com.paint.command.DrawCommand;
import com.paint.command.CommandHistory;

import com.paint.decorator.BorderDecorator;
import com.paint.decorator.FillDecorator;
import com.paint.decorator.ShadowDecorator;

public class ShapeStrategy implements DrawStrategy {
    private ShapeFactory factory;
    private Canvas canvas;
    private CommandHistory history;
    private double startX, startY;
    private Shape currentShape;

    public ShapeStrategy(IShapeFactory shapeFactory, Canvas canvas, CommandHistory history) {
        this.factory = new ShapeFactory(shapeFactory);
        this.canvas = canvas;
        this.history = history;
    }

    @Override
    public void onPress(double x, double y) {
        startX = x;
        startY = y;
        currentShape = factory.createShape(AppState.getInstance().getCurrentColor());
        if (currentShape != null) {
            currentShape.setX(x);
            currentShape.setY(y);
            currentShape.setW(0);
            currentShape.setH(0);
            canvas.setPreviewShape(currentShape);
        }
    }

    @Override
    public void onDrag(double x, double y) {
        if (currentShape != null) {
            double minX = Math.min(startX, x);
            double minY = Math.min(startY, y);
            double width = Math.abs(startX - x);
            double height = Math.abs(startY - y);
            
            currentShape.setX(minX);
            currentShape.setY(minY);
            currentShape.setW(width);
            currentShape.setH(height);
            canvas.setPreviewShape(currentShape);
        }
    }

    @Override
    public void onRelease(double x, double y) {
        if (currentShape != null) {
            canvas.setPreviewShape(null);
            
            // Apply Decorators from AppState
            AppState state = AppState.getInstance();
            Shape finalShape = currentShape;
            if (state.isUseFill()) {
                finalShape = new FillDecorator(finalShape, state.getSecondaryColor());
            }
            if (state.isUseBorder()) {
                finalShape = new BorderDecorator(finalShape, Color.BLACK, 2.0);
            }
            if (state.isUseShadow()) {
                finalShape = new ShadowDecorator(finalShape, Color.GRAY, 5);
            }
            
            DrawCommand cmd = new DrawCommand(finalShape, canvas);
            history.execute(cmd);
            currentShape = null;
        }
    }
}
