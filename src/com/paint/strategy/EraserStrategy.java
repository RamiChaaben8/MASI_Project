package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.singleton.AppState;

public class EraserStrategy implements DrawStrategy {
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        double size = AppState.getInstance().getBrushSize();
        gc.clearRect(x - size/2, y - size/2, size, size);
    }
}
