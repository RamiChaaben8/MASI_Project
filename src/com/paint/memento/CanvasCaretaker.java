package com.paint.memento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CanvasCaretaker {
    private Stack<CanvasMemento> mementos = new Stack<>();
    private static final String SAVE_FILE = "workspace_save.dat";

    public void save(CanvasMemento m) {
        mementos.push(m);
    }

    public CanvasMemento restore() {
        if (!mementos.isEmpty()) {
            return mementos.pop();
        }
        return null;
    }

    public void saveToFile(CanvasMemento m) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(m);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CanvasMemento loadFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            return (CanvasMemento) in.readObject();
        } catch (Exception e) {
            System.err.println("Load failed: " + e.getMessage());
            return null;
        }
    }
}
