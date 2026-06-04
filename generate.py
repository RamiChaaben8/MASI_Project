import os

base_dir = r"D:\MASI\workspace\Dp_project\src"

files = {
    "module-info.java": """module com.paint {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    opens com.paint.main to javafx.graphics, javafx.fxml;
}
""",
    "com/paint/main/Main.java": """/*
 * HOW TO RUN IN INTELLIJ IDEA
 * ----------------------------
 * 1. Go to Run > Edit Configurations
 * 2. Add VM Options:
 *    --module-path C:\\javafx-sdk-21.0.10\\lib
 *    --add-modules javafx.controls,javafx.fxml,javafx.graphics
 * 3. Click Apply then Run
 */
package com.paint.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.paint.observer.Canvas;
import com.paint.observer.UIPanel;
import com.paint.singleton.AppState;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        Canvas canvas = new Canvas();
        UIPanel uiPanel = new UIPanel();

        root.setCenter(canvas);
        root.setTop(uiPanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX Paint App");
        primaryStage.setScene(scene);
        primaryStage.show();

        AppState.getInstance().notifyObservers();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
""",
    "com/paint/singleton/AppState.java": """package com.paint.singleton;

import com.paint.observer.IObserver;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static AppState instance;
    private List<IObserver> observers = new ArrayList<>();

    private Color currentColor = Color.BLACK;
    private double brushSize = 5.0;
    private String currentTool = "FREEHAND";
    private String currentShape = "CIRCLE";

    private AppState() {}

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    public void notifyObservers() {
        for (IObserver observer : observers) {
            observer.update();
        }
    }

    public Color getCurrentColor() { return currentColor; }
    public void setCurrentColor(Color color) {
        this.currentColor = color;
        notifyObservers();
    }

    public double getBrushSize() { return brushSize; }
    public void setBrushSize(double size) {
        this.brushSize = size;
        notifyObservers();
    }

    public String getCurrentTool() { return currentTool; }
    public void setCurrentTool(String tool) {
        this.currentTool = tool;
        notifyObservers();
    }

    public String getCurrentShape() { return currentShape; }
    public void setCurrentShape(String shape) {
        this.currentShape = shape;
        notifyObservers();
    }
}
""",
    "com/paint/singleton/ToolManager.java": """package com.paint.singleton;

import com.paint.strategy.DrawStrategy;
import javafx.scene.canvas.GraphicsContext;

public class ToolManager {
    private static ToolManager instance;
    private DrawStrategy currentStrategy;

    private ToolManager() {}

    public static ToolManager getInstance() {
        if (instance == null) {
            instance = new ToolManager();
        }
        return instance;
    }

    public void setStrategy(DrawStrategy strategy) {
        this.currentStrategy = strategy;
    }

    public void executeDraw(GraphicsContext gc, double x, double y) {
        if (currentStrategy != null) {
            currentStrategy.draw(gc, x, y);
        }
    }
}
""",
    "com/paint/observer/IObserver.java": """package com.paint.observer;

public interface IObserver {
    void update();
}
""",
    "com/paint/observer/Canvas.java": """package com.paint.observer;

import com.paint.singleton.AppState;
import com.paint.singleton.ToolManager;
import com.paint.strategy.FreehandStrategy;
import com.paint.strategy.ShapeStrategy;
import com.paint.strategy.EraserStrategy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import com.paint.factory.Shape;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends Pane implements IObserver {
    private javafx.scene.canvas.Canvas innerCanvas;
    private GraphicsContext gc;
    private List<Shape> shapes = new ArrayList<>();

    public Canvas() {
        innerCanvas = new javafx.scene.canvas.Canvas(800, 600);
        gc = innerCanvas.getGraphicsContext2D();
        getChildren().add(innerCanvas);

        AppState.getInstance().addObserver(this);

        innerCanvas.setOnMousePressed(e -> {
            updateStrategy();
            ToolManager.getInstance().executeDraw(gc, e.getX(), e.getY());
        });

        innerCanvas.setOnMouseDragged(e -> {
            ToolManager.getInstance().executeDraw(gc, e.getX(), e.getY());
        });
    }

    public void updateStrategy() {
        String tool = AppState.getInstance().getCurrentTool();
        if (tool.equals("FREEHAND")) {
            ToolManager.getInstance().setStrategy(new FreehandStrategy());
        } else if (tool.equals("SHAPE")) {
            ToolManager.getInstance().setStrategy(new ShapeStrategy());
        } else if (tool.equals("ERASER")) {
            ToolManager.getInstance().setStrategy(new EraserStrategy());
        }
    }

    @Override
    public void update() {
        // Redraw logic if needed
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        shape.draw(gc);
    }

    public void removeShape(Shape shape) {
        shapes.remove(shape);
        redrawAll();
    }

    public void clear() {
        shapes.clear();
        redrawAll();
    }

    public void redrawAll() {
        gc.clearRect(0, 0, innerCanvas.getWidth(), innerCanvas.getHeight());
        for (Shape s : shapes) {
            s.draw(gc);
        }
    }

    public List<Shape> getShapes() {
        return new ArrayList<>(shapes);
    }

    public void setShapes(List<Shape> newShapes) {
        this.shapes = new ArrayList<>(newShapes);
        redrawAll();
    }
}
""",
    "com/paint/observer/UIPanel.java": """package com.paint.observer;

import com.paint.singleton.AppState;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;

public class UIPanel extends HBox implements IObserver {

    public UIPanel() {
        setSpacing(10);

        Button btnFreehand = new Button("Freehand");
        btnFreehand.setOnAction(e -> AppState.getInstance().setCurrentTool("FREEHAND"));

        Button btnShape = new Button("Shape");
        btnShape.setOnAction(e -> AppState.getInstance().setCurrentTool("SHAPE"));

        Button btnEraser = new Button("Eraser");
        btnEraser.setOnAction(e -> AppState.getInstance().setCurrentTool("ERASER"));

        ComboBox<String> shapeCombo = new ComboBox<>();
        shapeCombo.getItems().addAll("CIRCLE", "RECT", "LINE");
        shapeCombo.setValue("CIRCLE");
        shapeCombo.setOnAction(e -> AppState.getInstance().setCurrentShape(shapeCombo.getValue()));

        ColorPicker colorPicker = new ColorPicker(Color.BLACK);
        colorPicker.setOnAction(e -> AppState.getInstance().setCurrentColor(colorPicker.getValue()));

        getChildren().addAll(btnFreehand, btnShape, btnEraser, shapeCombo, colorPicker);

        AppState.getInstance().addObserver(this);
    }

    @Override
    public void update() {
        // Update UI states based on AppState
    }
}
""",
    "com/paint/strategy/DrawStrategy.java": """package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;

public interface DrawStrategy {
    void draw(GraphicsContext gc, double x, double y);
}
""",
    "com/paint/strategy/FreehandStrategy.java": """package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.singleton.AppState;

public class FreehandStrategy implements DrawStrategy {
    private boolean isFirstPoint = true;

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        gc.setStroke(AppState.getInstance().getCurrentColor());
        gc.setLineWidth(AppState.getInstance().getBrushSize());

        if (isFirstPoint) {
            gc.beginPath();
            gc.moveTo(x, y);
            isFirstPoint = false;
        } else {
            gc.lineTo(x, y);
            gc.stroke();
        }
    }
}
""",
    "com/paint/strategy/EraserStrategy.java": """package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.singleton.AppState;

public class EraserStrategy implements DrawStrategy {
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        double size = AppState.getInstance().getBrushSize() * 2;
        gc.clearRect(x - size/2, y - size/2, size, size);
    }
}
""",
    "com/paint/strategy/ShapeStrategy.java": """package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.singleton.AppState;
import com.paint.factory.Shape;
import com.paint.factory.ShapeFactory;

public class ShapeStrategy implements DrawStrategy {
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        String type = AppState.getInstance().getCurrentShape();
        javafx.scene.paint.Color color = AppState.getInstance().getCurrentColor();

        Shape shape = ShapeFactory.createShape(type, color);
        if (shape != null) {
            shape.setX(x);
            shape.setY(y);
            shape.draw(gc);
        }
    }
}
""",
    "com/paint/factory/Shape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {
    protected Color color;
    protected double x;
    protected double y;

    public Shape(Color color) {
        this.color = color;
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }

    public double getX() { return x; }
    public double getY() { return y; }
    public Color getColor() { return color; }

    public abstract void draw(GraphicsContext gc);
}
""",
    "com/paint/factory/ShapeFactory.java": """package com.paint.factory;

import javafx.scene.paint.Color;

public class ShapeFactory {
    public static Shape createShape(String type, Color color) {
        if ("CIRCLE".equalsIgnoreCase(type)) {
            return new CircleShape(color);
        } else if ("RECT".equalsIgnoreCase(type)) {
            return new RectShape(color);
        } else if ("LINE".equalsIgnoreCase(type)) {
            return new LineShape(color);
        }
        return null;
    }
}
""",
    "com/paint/factory/CircleShape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleShape extends Shape {
    public CircleShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillOval(x - 25, y - 25, 50, 50);
    }
}
""",
    "com/paint/factory/RectShape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectShape extends Shape {
    public RectShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);
        gc.fillRect(x - 25, y - 25, 50, 50);
    }
}
""",
    "com/paint/factory/LineShape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineShape extends Shape {
    public LineShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.setLineWidth(2);
        gc.strokeLine(x, y, x + 50, y + 50);
    }
}
""",
    "com/paint/decorator/ShapeDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeDecorator extends Shape {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        super(decoratedShape.getColor());
        this.decoratedShape = decoratedShape;
        this.setX(decoratedShape.getX());
        this.setY(decoratedShape.getY());
    }

    @Override
    public void draw(GraphicsContext gc) {
        decoratedShape.draw(gc);
    }
}
""",
    "com/paint/decorator/FillDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FillDecorator extends ShapeDecorator {
    private Color fillColor;

    public FillDecorator(Shape decoratedShape, Color fillColor) {
        super(decoratedShape);
        this.fillColor = fillColor;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(fillColor);
        decoratedShape.draw(gc);
    }
}
""",
    "com/paint/decorator/BorderDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BorderDecorator extends ShapeDecorator {
    private Color borderColor;
    private double borderWidth;

    public BorderDecorator(Shape decoratedShape, Color borderColor, double borderWidth) {
        super(decoratedShape);
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    @Override
    public void draw(GraphicsContext gc) {
        decoratedShape.draw(gc);
        gc.setStroke(borderColor);
        gc.setLineWidth(borderWidth);
        // Would normally draw just the border here based on shape
    }
}
""",
    "com/paint/decorator/ShadowDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class ShadowDecorator extends ShapeDecorator {
    private Color shadowColor;
    private double radius;

    public ShadowDecorator(Shape decoratedShape, Color shadowColor, double radius) {
        super(decoratedShape);
        this.shadowColor = shadowColor;
        this.radius = radius;
    }

    @Override
    public void draw(GraphicsContext gc) {
        DropShadow ds = new DropShadow();
        ds.setRadius(radius);
        ds.setColor(shadowColor);
        gc.setEffect(ds);
        decoratedShape.draw(gc);
        gc.setEffect(null);
    }
}
""",
    "com/paint/command/ICommand.java": """package com.paint.command;

public interface ICommand {
    void execute();
    void undo();
}
""",
    "com/paint/command/CommandHistory.java": """package com.paint.command;

import java.util.Stack;

public class CommandHistory {
    private Stack<ICommand> history = new Stack<>();

    public void executeCommand(ICommand command) {
        command.execute();
        history.push(command);
    }

    public void undoCommand() {
        if (!history.isEmpty()) {
            ICommand command = history.pop();
            command.undo();
        }
    }
}
""",
    "com/paint/command/DrawCommand.java": """package com.paint.command;

import com.paint.factory.Shape;
import com.paint.observer.Canvas;

public class DrawCommand implements ICommand {
    private Shape shape;
    private Canvas canvas;

    public DrawCommand(Shape shape, Canvas canvas) {
        this.shape = shape;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        canvas.addShape(shape);
    }

    @Override
    public void undo() {
        canvas.removeShape(shape);
    }
}
""",
    "com/paint/command/DeleteCommand.java": """package com.paint.command;

import com.paint.factory.Shape;
import com.paint.observer.Canvas;

public class DeleteCommand implements ICommand {
    private Shape shape;
    private Canvas canvas;

    public DeleteCommand(Shape shape, Canvas canvas) {
        this.shape = shape;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        canvas.removeShape(shape);
    }

    @Override
    public void undo() {
        canvas.addShape(shape);
    }
}
""",
    "com/paint/command/ClearCommand.java": """package com.paint.command;

import com.paint.observer.Canvas;
import com.paint.memento.CanvasMemento;

public class ClearCommand implements ICommand {
    private Canvas canvas;
    private CanvasMemento memento;

    public ClearCommand(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        memento = new CanvasMemento(canvas.getShapes());
        canvas.clear();
    }

    @Override
    public void undo() {
        if (memento != null) {
            canvas.setShapes(memento.getSavedShapes());
        }
    }
}
""",
    "com/paint/memento/CanvasMemento.java": """package com.paint.memento;

import com.paint.factory.Shape;
import java.util.ArrayList;
import java.util.List;

public class CanvasMemento {
    private List<Shape> savedShapes;

    public CanvasMemento(List<Shape> shapes) {
        this.savedShapes = new ArrayList<>(shapes);
    }

    public List<Shape> getSavedShapes() {
        return new ArrayList<>(savedShapes);
    }
}
""",
    "com/paint/memento/CanvasCaretaker.java": """package com.paint.memento;

import java.util.Stack;

public class CanvasCaretaker {
    private Stack<CanvasMemento> history = new Stack<>();

    public void save(CanvasMemento m) {
        history.push(m);
    }

    public CanvasMemento restore() {
        if (!history.isEmpty()) {
            return history.pop();
        }
        return null;
    }
}
"""
}

for filepath, content in files.items():
    full_path = os.path.join(base_dir, filepath)
    os.makedirs(os.path.dirname(full_path), exist_ok=True)
    with open(full_path, "w", encoding="utf-8") as f:
        f.write(content)

