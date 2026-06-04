package com.paint.observer;

import com.paint.singleton.AppState;
import com.paint.singleton.ToolManager;
import com.paint.strategy.EraserStrategy;
import com.paint.strategy.FreehandStrategy;
import com.paint.strategy.ShapeStrategy;
import com.paint.command.CommandHistory;
import com.paint.command.ClearCommand;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.VBox;

public class UIPanel extends VBox implements IObserver {
    private ColorPicker colorPicker;

    public UIPanel(Canvas canvas, CommandHistory history) {
        setSpacing(10);
        setPadding(new Insets(10));
        AppState.getInstance().registerObserver(this);

        Button btnFreehand = new Button("Freehand");
        btnFreehand.setOnAction(e -> ToolManager.getInstance().setStrategy(new FreehandStrategy()));

        Button btnEraser = new Button("Eraser");
        btnEraser.setOnAction(e -> ToolManager.getInstance().setStrategy(new EraserStrategy()));

        Button btnCircle = new Button("Circle");
        btnCircle.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("CIRCLE")));

        Button btnRect = new Button("Rectangle");
        btnRect.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("RECTANGLE")));

        Button btnLine = new Button("Line");
        btnLine.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("LINE")));

        Button btnClear = new Button("Clear Canvas");
        btnClear.setOnAction(e -> {
            ClearCommand clearCmd = new ClearCommand(canvas);
            clearCmd.execute();
            history.push(clearCmd);
        });

        Button btnUndo = new Button("Undo");
        btnUndo.setOnAction(e -> history.undo());

        colorPicker = new ColorPicker(AppState.getInstance().getCurrentColor());
        colorPicker.setOnAction(e -> AppState.getInstance().setCurrentColor(colorPicker.getValue()));

        getChildren().addAll(btnFreehand, btnEraser, btnCircle, btnRect, btnLine, colorPicker, btnClear, btnUndo);
    }

    @Override
    public void update() {
        colorPicker.setValue(AppState.getInstance().getCurrentColor());
    }
}