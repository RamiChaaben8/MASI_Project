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
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

import com.paint.command.DecorateAllCommand;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

import com.paint.memento.CanvasCaretaker;
import com.paint.memento.CanvasMemento;

public class UIPanel extends VBox implements IObserver {
    private ColorPicker colorPicker;
    private ColorPicker secondaryColorPicker;
    private Slider sizeSlider;
    private CheckBox cbFill;
    private CheckBox cbBorder;
    private CheckBox cbShadow;
    private CanvasCaretaker caretaker = new CanvasCaretaker();

    public UIPanel(Canvas canvas, CommandHistory history) {
        setSpacing(10);
        setPadding(new Insets(10));
        AppState.getInstance().addObserver(this);

        Button btnFreehand = new Button("Freehand");
        btnFreehand.setOnAction(e -> ToolManager.getInstance().setStrategy(new FreehandStrategy(canvas, history)));

        Button btnEraser = new Button("Eraser");
        btnEraser.setOnAction(e -> ToolManager.getInstance().setStrategy(new EraserStrategy(canvas, history)));

        Button btnCircle = new Button("Circle");
        btnCircle.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("CIRCLE", canvas, history)));

        Button btnRect = new Button("Rectangle");
        btnRect.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("RECT", canvas, history)));

        Button btnLine = new Button("Line");
        btnLine.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("LINE", canvas, history)));

        colorPicker = new ColorPicker(AppState.getInstance().getCurrentColor());
        colorPicker.setOnAction(e -> AppState.getInstance().setColor(colorPicker.getValue()));

        Label secColorLabel = new Label("Decorator Color:");
        secondaryColorPicker = new ColorPicker(AppState.getInstance().getSecondaryColor());
        secondaryColorPicker.setOnAction(e -> AppState.getInstance().setSecondaryColor(secondaryColorPicker.getValue()));

        Label sizeLabel = new Label("Brush Size:");
        sizeSlider = new Slider(1, 100, AppState.getInstance().getBrushSize());
        sizeSlider.setShowTickLabels(true);
        sizeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            AppState.getInstance().setBrushSize(newVal.intValue());
        });

        cbFill = new CheckBox("Fill");
        cbFill.setOnAction(e -> AppState.getInstance().setUseFill(cbFill.isSelected()));

        cbBorder = new CheckBox("Border");
        cbBorder.setOnAction(e -> AppState.getInstance().setUseBorder(cbBorder.isSelected()));

        cbShadow = new CheckBox("Shadow");
        cbShadow.setOnAction(e -> AppState.getInstance().setUseShadow(cbShadow.isSelected()));

        Button btnDecorateAll = new Button("Decorate All Existing");
        btnDecorateAll.setOnAction(e -> history.execute(new DecorateAllCommand(canvas)));
        
        Button btnSave = new Button("Save Workspace");
        btnSave.setOnAction(e -> caretaker.saveToFile(canvas.createMemento()));

        Button btnLoad = new Button("Load Workspace");
        btnLoad.setOnAction(e -> {
            CanvasMemento m = caretaker.loadFromFile();
            if (m != null) canvas.restore(m);
        });

        Button btnClear = new Button("Clear");
        btnClear.setOnAction(e -> {
            ClearCommand clearCmd = new ClearCommand(canvas);
            history.execute(clearCmd);
        });

        Button btnUndo = new Button("Undo");
        btnUndo.setOnAction(e -> history.undo());
        
        Button btnRedo = new Button("Redo");
        btnRedo.setOnAction(e -> history.redo());

        getChildren().addAll(
            btnFreehand, btnEraser, btnCircle, btnRect, btnLine, 
            new Label("Primary Color:"), colorPicker, 
            secColorLabel, secondaryColorPicker,
            sizeLabel, sizeSlider,
            new Label("Decorators:"), cbFill, cbBorder, cbShadow,
            btnDecorateAll, btnSave, btnLoad, btnClear, btnUndo, btnRedo
        );
    }
    
    public void refreshToolbar() {
        colorPicker.setValue(AppState.getInstance().getCurrentColor());
        secondaryColorPicker.setValue(AppState.getInstance().getSecondaryColor());
        sizeSlider.setValue(AppState.getInstance().getBrushSize());
        cbFill.setSelected(AppState.getInstance().isUseFill());
        cbBorder.setSelected(AppState.getInstance().isUseBorder());
        cbShadow.setSelected(AppState.getInstance().isUseShadow());
    }

    @Override
    public void update() {
        refreshToolbar();
    }
}
