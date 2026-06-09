package com.paint.memento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CanvasCaretaker {
    private static final String SAVE_FILE = "workspace_save.dat";

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
