package com.paint.observer;

import com.paint.singleton.AppState;
import com.paint.singleton.ToolManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Canvas extends javafx.scene.canvas.Canvas implements IObserver {
    
    public Canvas(double width, double height) {
        super(width, height);
        AppState.getInstance().registerObserver(this);
        
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, width, height);
        
        this.setOnMousePressed(e -> ToolManager.getInstance().executeDraw(e, gc));
        this.setOnMouseDragged(e -> ToolManager.getInstance().executeDraw(e, gc));
        this.setOnMouseReleased(e -> ToolManager.getInstance().executeDraw(e, gc));
        
        update();
    }

    @Override
    public void update() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setStroke(AppState.getInstance().getCurrentColor());
        gc.setLineWidth(AppState.getInstance().getCurrentLineWidth());
    }
}