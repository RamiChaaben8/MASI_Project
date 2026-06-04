package com.paint.command;

import com.paint.factory.Shape;
import com.paint.memento.CanvasMemento;
import com.paint.observer.Canvas;

public class DrawCommand implements ICommand {
    private Shape shape;
    private Canvas canvas;
    private CanvasMemento previousState;

    public DrawCommand(Canvas canvas, Shape shape) {
        this.canvas = canvas;
        this.shape = shape;
    }

    @Override
    public void execute() {
        // Assume saving memento of the canvas image before drawing for undo
        previousState = new CanvasMemento(canvas.snapshot(null, null));
        shape.draw(canvas.getGraphicsContext2D());
    }

    @Override
    public void undo() {
        if (previousState != null) {
            canvas.getGraphicsContext2D().drawImage(previousState.getSnapshot(), 0, 0);
        }
    }
}