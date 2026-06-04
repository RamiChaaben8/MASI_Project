package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class EraserStrategy implements DrawStrategy {
    @Override
    public void draw(MouseEvent event, GraphicsContext gc) {
        if (event.getEventType() == MouseEvent.MOUSE_DRAGGED || event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            gc.setFill(Color.WHITE);
            double size = gc.getLineWidth() * 5; 
            gc.fillRect(event.getX() - size/2, event.getY() - size/2, size, size);
        }
    }
}