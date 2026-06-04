package com.paint.singleton;

import com.paint.strategy.DrawStrategy;
import com.paint.strategy.FreehandStrategy;
import javafx.scene.canvas.GraphicsContext;

public class ToolManager {
    private static ToolManager instance;
    private DrawStrategy strategy;

    private ToolManager() {
        this.strategy = new FreehandStrategy();
    }

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }

    public void setStrategy(DrawStrategy s) {
        this.strategy = s;
    }

    public void executeDraw(GraphicsContext gc, double x, double y) {
        if (strategy != null) {
            strategy.draw(gc, x, y);
        }
    }
}
