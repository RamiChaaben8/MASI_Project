package com.paint.memento;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CanvasCaretaker {
    private Stack<CanvasMemento> mementos = new Stack<>();
    private static final String SAVE_FILE = "workspace_save.txt";

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
        try (PrintWriter out = new PrintWriter(new FileWriter(SAVE_FILE))) {
            out.println(m.getBrushSize());
            for (com.paint.factory.Shape s : m.getShapes()) {
                // We will implement a simplified serialization
                out.println(s.getClass().getSimpleName() + ":" + s.getX() + "," + s.getY() + "," + s.getW() + "," + s.getH() + "," + s.getColor());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CanvasMemento loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SAVE_FILE))) {
            int brushSize = Integer.parseInt(reader.readLine());
            List<com.paint.factory.Shape> shapes = new ArrayList<>();
            String line;
            com.paint.factory.ShapeFactory factory = new com.paint.factory.ShapeFactory();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String type = parts[0].replace("Shape", "").replace("Decorator", "").toUpperCase();
                String[] data = parts[1].split(",");
                
                double x = Double.parseDouble(data[0]);
                double y = Double.parseDouble(data[1]);
                double w = Double.parseDouble(data[2]);
                double h = Double.parseDouble(data[3]);
                javafx.scene.paint.Color color = javafx.scene.paint.Color.valueOf(data[4]);
                
                com.paint.factory.Shape s = factory.createShape(type, color);
                if (s != null) {
                    s.setX(x);
                    s.setY(y);
                    s.setW(w);
                    s.setH(h);
                    shapes.add(s);
                }
            }
            return new CanvasMemento(shapes, brushSize);
        } catch (Exception e) {
            System.err.println("Load failed: " + e.getMessage());
            return null;
        }
    }
}
