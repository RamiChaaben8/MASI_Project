package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.singleton.AppState;

public class FreehandStrategy implements DrawStrategy {
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.setLineWidth(AppState.getInstance().getBrushSize());
        gc.setStroke(AppState.getInstance().getCurrentColor());
        gc.lineTo(x, y);
        gc.stroke();
    }
}
