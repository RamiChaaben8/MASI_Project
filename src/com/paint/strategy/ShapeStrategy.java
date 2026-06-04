package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.factory.ShapeFactory;
import com.paint.factory.Shape;
import com.paint.singleton.AppState;
import com.paint.observer.Canvas;
import com.paint.command.DrawCommand;
import com.paint.command.CommandHistory;

public class ShapeStrategy implements DrawStrategy {
    private ShapeFactory factory;
    private String shapeType;
    private Canvas canvas;
    private CommandHistory history;

    public ShapeStrategy(String shapeType, Canvas canvas, CommandHistory history) {
        this.factory = new ShapeFactory();
        this.shapeType = shapeType;
        this.canvas = canvas;
        this.history = history;
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        Shape shape = factory.createShape(shapeType, AppState.getInstance().getCurrentColor());
        if (shape != null) {
            shape.setX(x);
            shape.setY(y);
            shape.setW(50); // Default width
            shape.setH(50); // Default height
            
            DrawCommand cmd = new DrawCommand(shape, canvas);
            history.execute(cmd);
        }
    }
}
