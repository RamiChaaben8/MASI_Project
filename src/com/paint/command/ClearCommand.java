package com.paint.command;

import com.paint.memento.CanvasMemento;
import com.paint.observer.Canvas;

public class ClearCommand implements ICommand {
    private Canvas canvas;
    private CanvasMemento memento;

    public ClearCommand(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        memento = canvas.createMemento();
        canvas.clearShapes();
    }

    @Override
    public void undo() {
        if (memento != null) {
            canvas.restore(memento);
        }
    }
}
