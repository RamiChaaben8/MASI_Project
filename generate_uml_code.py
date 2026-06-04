import os

files = {
"src/com/paint/singleton/AppState.java": """package com.paint.singleton;

import com.paint.observer.IObserver;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static AppState instance;
    private Color currentColor = Color.BLACK;
    private int brushSize = 2;
    private List<IObserver> observers = new ArrayList<>();

    private AppState() { }

    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void setColor(Color c) {
        this.currentColor = c;
        notifyObservers();
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public void setBrushSize(int brushSize) {
        this.brushSize = brushSize;
        notifyObservers();
    }

    public int getBrushSize() {
        return brushSize;
    }

    public void addObserver(IObserver o) {
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    public void notifyObservers() {
        for (IObserver o : observers) {
            o.update();
        }
    }
}
""",

"src/com/paint/singleton/ToolManager.java": """package com.paint.singleton;

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
""",

"src/com/paint/strategy/DrawStrategy.java": """package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;

public interface DrawStrategy {
    void draw(GraphicsContext gc, double x, double y);
}
""",

"src/com/paint/strategy/FreehandStrategy.java": """package com.paint.strategy;

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
""",

"src/com/paint/strategy/ShapeStrategy.java": """package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.factory.ShapeFactory;
import com.paint.factory.Shape;
import com.paint.singleton.AppState;
import com.paint.observer.Canvas;
import com.paint.command.DrawCommand;
import com.paint.command.CommandHistory;

public class ShapeStrategy implements DrawStrategy {
    private ShapeFactory factory;
    private String shapeType;
    private Canvas canvas;
    private CommandHistory history;

    public ShapeStrategy(String shapeType, Canvas canvas, CommandHistory history) {
        this.factory = new ShapeFactory();
        this.shapeType = shapeType;
        this.canvas = canvas;
        this.history = history;
    }

    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        Shape shape = factory.createShape(shapeType, AppState.getInstance().getCurrentColor());
        if (shape != null) {
            shape.setX(x);
            shape.setY(y);
            shape.setW(50); // Default width
            shape.setH(50); // Default height
            
            DrawCommand cmd = new DrawCommand(shape, canvas);
            history.execute(cmd);
        }
    }
}
""",

"src/com/paint/strategy/EraserStrategy.java": """package com.paint.strategy;

import javafx.scene.canvas.GraphicsContext;
import com.paint.singleton.AppState;

public class EraserStrategy implements DrawStrategy {
    @Override
    public void draw(GraphicsContext gc, double x, double y) {
        double size = AppState.getInstance().getBrushSize();
        gc.clearRect(x - size/2, y - size/2, size, size);
    }
}
""",

"src/com/paint/factory/ShapeFactory.java": """package com.paint.factory;

import javafx.scene.paint.Color;

public class ShapeFactory {
    public Shape createShape(String type, Color color) {
        if (type == null) {
            return null;
        }
        if (type.equalsIgnoreCase("CIRCLE")) {
            return new CircleShape(color);
        } else if (type.equalsIgnoreCase("RECT")) {
            return new RectShape(color);
        } else if (type.equalsIgnoreCase("LINE")) {
            return new LineShape(color);
        }
        return null;
    }
}
""",

"src/com/paint/factory/Shape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape implements Cloneable {
    protected Color color;
    protected double x, y;
    protected double w, h;

    public Shape(Color color) {
        this.color = color;
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setW(double w) { this.w = w; }
    public void setH(double h) { this.h = h; }
    
    public double getX() { return x; }
    public double getY() { return y; }
    public double getW() { return w; }
    public double getH() { return h; }
    public Color getColor() { return color; }

    public abstract void draw(GraphicsContext gc);

    @Override
    public Shape clone() {
        try {
            return (Shape) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}
""",

"src/com/paint/factory/CircleShape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleShape extends Shape {
    public CircleShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeOval(x, y, w, h);
    }
}
""",

"src/com/paint/factory/RectShape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectShape extends Shape {
    public RectShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeRect(x, y, w, h);
    }
}
""",

"src/com/paint/factory/LineShape.java": """package com.paint.factory;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class LineShape extends Shape {
    public LineShape(Color color) {
        super(color);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setStroke(color);
        gc.strokeLine(x, y, x + w, y + h);
    }
}
""",

"src/com/paint/decorator/ShapeDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeDecorator extends Shape {
    protected Shape wrapped;

    public ShapeDecorator(Shape s) {
        super(s != null ? s.getColor() : null);
        this.wrapped = s;
        if (s != null) {
            this.x = s.getX();
            this.y = s.getY();
            this.w = s.getW();
            this.h = s.getH();
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (wrapped != null) {
            wrapped.draw(gc);
        }
    }
    
    @Override
    public Shape clone() {
        ShapeDecorator clone = (ShapeDecorator) super.clone();
        if (this.wrapped != null) {
            clone.wrapped = this.wrapped.clone();
        }
        return clone;
    }
}
""",

"src/com/paint/decorator/FillDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FillDecorator extends ShapeDecorator {
    private Color fillColor;

    public FillDecorator(Shape s, Color fillColor) {
        super(s);
        this.fillColor = fillColor;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Color oldFill = (Color) gc.getFill();
        gc.setFill(fillColor);
        if (wrapped != null) {
            // Basic fill logic for demonstration
            gc.fillRect(wrapped.getX(), wrapped.getY(), wrapped.getW(), wrapped.getH()); 
        }
        super.draw(gc); // delegates to wrapped.draw(gc)
        gc.setFill(oldFill);
    }
}
""",

"src/com/paint/decorator/BorderDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BorderDecorator extends ShapeDecorator {
    private Color strokeColor;
    private double strokeWidth;

    public BorderDecorator(Shape s, Color strokeColor, double strokeWidth) {
        super(s);
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    @Override
    public void draw(GraphicsContext gc) {
        Color oldStroke = (Color) gc.getStroke();
        double oldWidth = gc.getLineWidth();
        
        gc.setStroke(strokeColor);
        gc.setLineWidth(strokeWidth);
        
        super.draw(gc);
        
        gc.setStroke(oldStroke);
        gc.setLineWidth(oldWidth);
    }
}
""",

"src/com/paint/decorator/ShadowDecorator.java": """package com.paint.decorator;

import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;

public class ShadowDecorator extends ShapeDecorator {
    private Color shadowColor;
    private int offset;

    public ShadowDecorator(Shape s, Color shadowColor, int offset) {
        super(s);
        this.shadowColor = shadowColor;
        this.offset = offset;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.save();
        DropShadow ds = new DropShadow();
        ds.setOffsetY(offset);
        ds.setOffsetX(offset);
        ds.setColor(shadowColor);
        gc.setEffect(ds);
        
        super.draw(gc);
        
        gc.restore();
    }
}
""",

"src/com/paint/memento/CanvasMemento.java": """package com.paint.memento;

import com.paint.factory.Shape;
import java.util.ArrayList;
import java.util.List;

public class CanvasMemento {
    private List<Shape> shapes;
    private int brushSize;

    public CanvasMemento(List<Shape> shapes, int brushSize) {
        this.shapes = new ArrayList<>();
        for (Shape s : shapes) {
            this.shapes.add(s.clone());
        }
        this.brushSize = brushSize;
    }

    public List<Shape> getShapes() {
        return shapes;
    }
    
    public int getBrushSize() {
        return brushSize;
    }
}
""",

"src/com/paint/memento/CanvasCaretaker.java": """package com.paint.memento;

import java.util.Stack;

public class CanvasCaretaker {
    private Stack<CanvasMemento> mementos = new Stack<>();

    public void save(CanvasMemento m) {
        mementos.push(m);
    }

    public CanvasMemento restore() {
        if (!mementos.isEmpty()) {
            return mementos.pop();
        }
        return null;
    }
}
""",

"src/com/paint/command/CommandHistory.java": """package com.paint.command;

import java.util.Stack;

public class CommandHistory {
    private Stack<ICommand> history = new Stack<>();
    private Stack<ICommand> redoStack = new Stack<>();

    public void execute(ICommand cmd) {
        cmd.execute();
        history.push(cmd);
        redoStack.clear();
    }

    public void undo() {
        if (!history.isEmpty()) {
            ICommand cmd = history.pop();
            cmd.undo();
            redoStack.push(cmd);
        }
    }
    
    public void redo() {
        if (!redoStack.isEmpty()) {
            ICommand cmd = redoStack.pop();
            cmd.execute();
            history.push(cmd);
        }
    }
}
""",

"src/com/paint/command/ICommand.java": """package com.paint.command;

public interface ICommand {
    void execute();
    void undo();
}
""",

"src/com/paint/command/DrawCommand.java": """package com.paint.command;

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

"src/com/paint/command/DeleteCommand.java": """package com.paint.command;

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

"src/com/paint/command/ClearCommand.java": """package com.paint.command;

import com.paint.memento.CanvasMemento;
import com.paint.observer.Canvas;

public class ClearCommand implements ICommand {
    private Canvas canvas;
    private CanvasMemento memento;

    public ClearCommand(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        memento = canvas.createMemento();
        canvas.clearShapes();
    }

    @Override
    public void undo() {
        if (memento != null) {
            canvas.restore(memento);
        }
    }
}
""",

"src/com/paint/observer/IObserver.java": """package com.paint.observer;

public interface IObserver {
    void update();
}
""",

"src/com/paint/observer/Canvas.java": """package com.paint.observer;

import com.paint.singleton.AppState;
import com.paint.singleton.ToolManager;
import com.paint.memento.CanvasMemento;
import com.paint.factory.Shape;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends javafx.scene.canvas.Canvas implements IObserver {
    private List<Shape> shapes = new ArrayList<>();

    public Canvas(double width, double height) {
        super(width, height);
        AppState.getInstance().addObserver(this);
        
        this.setOnMousePressed(e -> ToolManager.getInstance().executeDraw(getGraphicsContext2D(), e.getX(), e.getY()));
        this.setOnMouseDragged(e -> ToolManager.getInstance().executeDraw(getGraphicsContext2D(), e.getX(), e.getY()));
        
        redraw();
    }

    public void addShape(Shape s) {
        shapes.add(s);
        redraw();
    }
    
    public void removeShape(Shape s) {
        shapes.remove(s);
        redraw();
    }
    
    public void clearShapes() {
        shapes.clear();
        redraw();
    }
    
    public CanvasMemento createMemento() {
        return new CanvasMemento(shapes, AppState.getInstance().getBrushSize());
    }
    
    public void restore(CanvasMemento m) {
        if (m != null) {
            this.shapes = new ArrayList<>();
            for (Shape s : m.getShapes()) {
                this.shapes.add(s.clone());
            }
            redraw();
        }
    }

    public void redraw() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        for (Shape shape : shapes) {
            shape.draw(gc);
        }
    }

    @Override
    public void update() {
        // AppState changed, we might need to redraw or prepare settings
    }
}
""",

"src/com/paint/observer/UIPanel.java": """package com.paint.observer;

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
        AppState.getInstance().addObserver(this);

        Button btnFreehand = new Button("Freehand");
        btnFreehand.setOnAction(e -> ToolManager.getInstance().setStrategy(new FreehandStrategy()));

        Button btnEraser = new Button("Eraser");
        btnEraser.setOnAction(e -> ToolManager.getInstance().setStrategy(new EraserStrategy()));

        Button btnCircle = new Button("Circle");
        btnCircle.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("CIRCLE", canvas, history)));

        Button btnRect = new Button("Rectangle");
        btnRect.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("RECT", canvas, history)));

        Button btnLine = new Button("Line");
        btnLine.setOnAction(e -> ToolManager.getInstance().setStrategy(new ShapeStrategy("LINE", canvas, history)));

        Button btnClear = new Button("Clear");
        btnClear.setOnAction(e -> {
            ClearCommand clearCmd = new ClearCommand(canvas);
            history.execute(clearCmd);
        });

        Button btnUndo = new Button("Undo");
        btnUndo.setOnAction(e -> history.undo());
        
        Button btnRedo = new Button("Redo");
        btnRedo.setOnAction(e -> history.redo());

        colorPicker = new ColorPicker(AppState.getInstance().getCurrentColor());
        colorPicker.setOnAction(e -> AppState.getInstance().setColor(colorPicker.getValue()));

        getChildren().addAll(btnFreehand, btnEraser, btnCircle, btnRect, btnLine, colorPicker, btnClear, btnUndo, btnRedo);
    }
    
    public void refreshToolbar() {
        // refresh logic
    }

    @Override
    public void update() {
        colorPicker.setValue(AppState.getInstance().getCurrentColor());
    }
}
""",

"src/com/paint/main/Main.java": """package com.paint.main;

import com.paint.observer.Canvas;
import com.paint.observer.UIPanel;
import com.paint.command.CommandHistory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        
        CommandHistory history = new CommandHistory();
        Canvas canvas = new Canvas(800, 600);
        UIPanel uiPanel = new UIPanel(canvas, history);
        
        root.setCenter(canvas);
        root.setLeft(uiPanel);
        
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("Paint App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
"""
}

for filepath, content in files.items():
    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

print("Generated all files correctly.")
