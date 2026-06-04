package com.paint.main;

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
