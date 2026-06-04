package com.paint.memento;

import java.util.Stack;

public class CanvasCaretaker {
    private Stack<CanvasMemento> mementos = new Stack<>();

    public void save(CanvasMemento m) {
        mementos.push(m);
    }

    public CanvasMemento restore() {
        if (!mementos.isEmpty()) {
            return mementos.pop();
        }
        return null;
    }
}
