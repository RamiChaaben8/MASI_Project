package com.paint.command;

import java.util.Stack;

public class CommandHistory {
    private Stack<ICommand> history = new Stack<>();

    public void push(ICommand cmd) {
        history.push(cmd);
    }

    public void undo() {
        if (!history.isEmpty()) {
            ICommand cmd = history.pop();
            cmd.undo();
        }
    }
}