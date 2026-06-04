package com.paint.memento;

import java.util.Stack;

public class CanvasCaretaker {
    private Stack<CanvasMemento> history = new Stack<>();

    public void saveMemento(CanvasMemento memento) {
        history.push(memento);
    }

    public CanvasMemento getLastMemento() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
}