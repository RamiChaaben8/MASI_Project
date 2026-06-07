package com.paint.singleton;

import com.paint.strategy.DrawStrategy;
import com.paint.strategy.FreehandStrategy;
import javafx.scene.canvas.GraphicsContext;

public class ToolManager {
    private DrawStrategy strategy;

    private ToolManager() {
        this.strategy = new FreehandStrategy();
    }
    private static final ToolManager instance=new ToolManager();

    public static ToolManager getInstance() {

        return instance;
    }

    public void setStrategy(DrawStrategy s) {
        this.strategy = s;
    }

    public void onPress(double x, double y) {
        if (strategy != null) strategy.onPress(x, y);
    }

    public void onDrag(double x, double y) {
        if (strategy != null) strategy.onDrag(x, y);
    }

    public void onRelease(double x, double y) {
        if (strategy != null) strategy.onRelease(x, y);
    }
}
