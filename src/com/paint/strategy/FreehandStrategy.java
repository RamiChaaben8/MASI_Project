package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class FreehandStrategy implements DrawStrategy {
    @Override
    public void draw(MouseEvent event, GraphicsContext gc) {
        if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        }
    }
}