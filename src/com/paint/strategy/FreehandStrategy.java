package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.singleton.AppState;

import com.paint.factory.FreehandShape;
import com.paint.observer.Canvas;
import com.paint.command.CommandHistory;
import com.paint.command.DrawCommand;

public class FreehandStrategy implements DrawStrategy {
    private Canvas canvas;
    private CommandHistory history;
    private FreehandShape currentShape;
    public FreehandStrategy(){}
    public FreehandStrategy(Canvas canvas, CommandHistory history) {
        this.canvas = canvas;
        this.history = history;
    }

    @Override
    public void onPress(double x, double y) {
        currentShape = new FreehandShape(
            AppState.getInstance().getCurrentColor(),
            AppState.getInstance().getBrushSize()
        );
        currentShape.addPoint(x, y);
        canvas.setPreviewShape(currentShape);
    }

    @Override
    public void onDrag(double x, double y) {
        if (currentShape != null) {
            currentShape.addPoint(x, y);
            canvas.setPreviewShape(currentShape);
        }
    }

    @Override
    public void onRelease(double x, double y) {
        if (currentShape != null) {
            canvas.setPreviewShape(null);
            DrawCommand cmd = new DrawCommand(currentShape, canvas);
            history.execute(cmd);
            currentShape = null;
        }
    }
}
