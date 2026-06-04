package com.paint.command;

import java.util.Stack;

public class CommandHistory {
    private Stack<ICommand> history = new Stack<>();
    private Stack<ICommand> redoStack = new Stack<>();

    public void execute(ICommand cmd) {
        cmd.execute();
        history.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        if (!history.isEmpty()) {
            ICommand cmd = history.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            ICommand cmd = redoStack.pop();
            cmd.execute();
            history.push(cmd);
        }
    }
}
