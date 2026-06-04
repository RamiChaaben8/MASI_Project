package com.paint.main;

import com.paint.observer.Canvas;
import com.paint.observer.UIPanel;
import com.paint.command.CommandHistory;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

// Run with: --module-path C:\javafx-sdk-21.0.10\lib --add-modules javafx.controls,javafx.fxml,javafx.graphics
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        
        Canvas canvas = new Canvas(800, 600);
        CommandHistory history = new CommandHistory();
        UIPanel uiPanel = new UIPanel(canvas, history);
        
        root.setCenter(canvas);
        root.setLeft(uiPanel);
        
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("JavaFX Pattern Paint");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}