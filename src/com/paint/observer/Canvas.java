package com.paint.observer;

import com.paint.singleton.AppState;
import com.paint.singleton.ToolManager;
import com.paint.memento.CanvasMemento;
import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends javafx.scene.canvas.Canvas implements IObserver {
    private List<Shape> shapes = new ArrayList<>();
    private Shape previewShape;

    public Canvas(double width, double height) {
        super(width, height);
        AppState.getInstance().addObserver(this);
        
        this.setOnMousePressed(e -> ToolManager.getInstance().onPress(e.getX(), e.getY()));
        this.setOnMouseDragged(e -> ToolManager.getInstance().onDrag(e.getX(), e.getY()));
        this.setOnMouseReleased(e -> ToolManager.getInstance().onRelease(e.getX(), e.getY()));
        
        redraw();
    }

    public void setPreviewShape(Shape previewShape) {
        this.previewShape = previewShape;
        redraw();
    }

    public void addShape(Shape s) {
        shapes.add(s);
        redraw();
    }
    
    public void removeShape(Shape s) {
        shapes.remove(s);
        redraw();
    }
    
    public void setShapes(List<Shape> shapes) {
        this.shapes = shapes;
        redraw();
    }
    
    public void clearShapes() {
        shapes.clear();
        redraw();
    }
    
    public CanvasMemento createMemento() {
        return new CanvasMemento(shapes, AppState.getInstance().getBrushSize());
    }
    
    public void restore(CanvasMemento m) {
        if (m != null) {
            this.shapes = new ArrayList<>();
            for (Shape s : m.getShapes()) {
                this.shapes.add(s.clone());
            }
            redraw();
        }
    }

    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
        
        if (previewShape != null) {
            previewShape.draw(gc);
        }
    }

    @Override
    public void update() {
        redraw();
    }
}
