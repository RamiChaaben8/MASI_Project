package com.paint.command;

import com.paint.factory.Shape;
import com.paint.observer.Canvas;

public class DrawCommand implements ICommand {
    private Shape shape;
    private Canvas canvas;

    public DrawCommand(Shape shape, Canvas canvas) {
        this.shape = shape;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        canvas.addShape(shape);
    }

    @Override
    public void undo() {
        canvas.removeShape(shape);
    }
}
