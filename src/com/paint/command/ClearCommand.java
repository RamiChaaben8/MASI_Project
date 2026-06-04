package com.paint.command;

import com.paint.memento.CanvasCaretaker;
import com.paint.memento.CanvasMemento;
import com.paint.observer.Canvas;
import javafx.scene.paint.Color;

public class ClearCommand implements ICommand {
    private Canvas canvas;
    private CanvasCaretaker caretaker;

    public ClearCommand(Canvas canvas) {
        this.canvas = canvas;
        this.caretaker = new CanvasCaretaker();
    }

    @Override
    public void execute() {
        // Save state before clearing
        caretaker.saveMemento(new CanvasMemento(canvas.snapshot(null, null)));
        
        canvas.getGraphicsContext2D().setFill(Color.WHITE);
        canvas.getGraphicsContext2D().fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    @Override
    public void undo() {
        CanvasMemento memento = caretaker.getLastMemento();
        if (memento != null) {
            canvas.getGraphicsContext2D().drawImage(memento.getSnapshot(), 0, 0);
        }
    }
}