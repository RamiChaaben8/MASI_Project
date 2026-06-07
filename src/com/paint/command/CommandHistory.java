package com.paint.command;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Stack;

public class CommandHistory {
    private Stack<ICommand> history = new Stack<>();
    private Stack<ICommand> redoStack = new Stack<>();
    private static final String LOG_FILE = "paint_app.log";

    private void logAction(String action) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(LocalDateTime.now() + " - " + action + "\n");
        } catch (IOException e) {
            System.err.println("Failed to write to log: " + e.getMessage());
        }
    }

    public void execute(ICommand cmd) {
        cmd.execute();
        history.push(cmd);
        redoStack.clear();
        logAction("Executed: " + cmd.getClass().getSimpleName());
    }

    public void undo() {
        if (!history.isEmpty()) {
            ICommand cmd = history.pop();
            cmd.undo();
            redoStack.push(cmd);
            logAction("Undid: " + cmd.getClass().getSimpleName());
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            ICommand cmd = redoStack.pop();
            cmd.execute();
            history.push(cmd);
            logAction("Redid: " + cmd.getClass().getSimpleName());
        }
    }
}
