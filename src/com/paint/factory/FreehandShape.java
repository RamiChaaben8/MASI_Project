package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class FreehandShape extends Shape {
    private List<double[]> points = new ArrayList<>();
    private double brushSize;

    public FreehandShape(Color color, double brushSize) {
        super(color);
        this.brushSize = brushSize;
    }

    public void addPoint(double x, double y) {
        points.add(new double[]{x, y});
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (points.isEmpty()) return;
        
        gc.setStroke(color);
        gc.setLineWidth(brushSize);
        gc.beginPath();
        double[] first = points.get(0);
        gc.moveTo(first[0], first[1]);
        
        for (int i = 1; i < points.size(); i++) {
            double[] p = points.get(i);
            gc.lineTo(p[0], p[1]);
        }
        gc.stroke();
    }

    @Override
    public Shape clone() {
        FreehandShape clone = (FreehandShape) super.clone();
        clone.points = new ArrayList<>(this.points);
        return clone;
    }
}
