package com.paint.command;

import com.paint.factory.Shape;
import com.paint.observer.Canvas;

public class DeleteCommand implements ICommand {
    private Shape shape;
    private Canvas canvas;

    public DeleteCommand(Shape shape, Canvas canvas) {
        this.shape = shape;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        canvas.removeShape(shape);
    }

    @Override
    public void undo() {
        canvas.addShape(shape);
    }
}
