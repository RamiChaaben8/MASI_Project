package com.paint.memento;

import com.paint.factory.Shape;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CanvasMemento implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Shape> shapes;
    private int brushSize;

    public CanvasMemento(List<Shape> shapes, int brushSize) {
        this.shapes = new ArrayList<>();
        for (Shape s : shapes) {
            this.shapes.add(s.clone());
        }
        this.brushSize = brushSize;
    }

    public List<Shape> getShapes() {
        return shapes;
    }
    
    public int getBrushSize() {
        return brushSize;
    }
}
