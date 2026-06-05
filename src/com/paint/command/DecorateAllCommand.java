package com.paint.command;

import com.paint.factory.Shape;
import com.paint.observer.Canvas;
import com.paint.memento.CanvasMemento;
import com.paint.singleton.AppState;
import com.paint.decorator.FillDecorator;
import com.paint.decorator.BorderDecorator;
import com.paint.decorator.ShadowDecorator;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class DecorateAllCommand implements ICommand {
    private Canvas canvas;
    private CanvasMemento oldState;

    public DecorateAllCommand(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        oldState = canvas.createMemento();
        
        List<Shape> shapes = oldState.getShapes();
        List<Shape> decoratedShapes = new ArrayList<>();
        
        AppState state = AppState.getInstance();
        
        for (Shape s : shapes) {
            Shape decorated = s.clone();
            if (state.isUseFill()) {
                decorated = new FillDecorator(decorated, state.getSecondaryColor());
            }
            if (state.isUseBorder()) {
                decorated = new BorderDecorator(decorated, Color.BLACK, 2.0);
            }
            if (state.isUseShadow()) {
                decorated = new ShadowDecorator(decorated, Color.GRAY, 5);
            }
            decoratedShapes.add(decorated);
        }
        
        canvas.setShapes(decoratedShapes);
    }

    @Override
    public void undo() {
        canvas.restore(oldState);
    }
}
