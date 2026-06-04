package com.paint.command;

import com.paint.memento.CanvasMemento;
import com.paint.observer.Canvas;

public class DeleteCommand implements ICommand {
    private Canvas canvas;
    private CanvasMemento previousState;

    public DeleteCommand(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        previousState = new CanvasMemento(canvas.snapshot(null, null));
        // Delete logic (e.g. erase specific area or element)
    }

    @Override
    public void undo() {
        if (previousState != null) {
            canvas.getGraphicsContext2D().drawImage(previousState.getSnapshot(), 0, 0);
        }
    }
}