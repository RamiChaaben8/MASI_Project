package com.paint.singleton;

import com.paint.strategy.DrawStrategy;
import com.paint.strategy.FreehandStrategy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public class ToolManager {
    private static ToolManager instance;
    private DrawStrategy currentStrategy;

    private ToolManager() {
        this.currentStrategy = new FreehandStrategy();
    }

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }

    public void setStrategy(DrawStrategy strategy) {
        this.currentStrategy = strategy;
    }

    public void executeDraw(MouseEvent event, GraphicsContext gc) {
        if (currentStrategy != null) {
            currentStrategy.draw(event, gc);
        }
    }
}