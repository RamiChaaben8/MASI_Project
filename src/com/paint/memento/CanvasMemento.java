package com.paint.memento;

import javafx.scene.image.Image;

public class CanvasMemento {
    private final Image snapshot;

    public CanvasMemento(Image snapshot) {
        this.snapshot = snapshot;
    }

    public Image getSnapshot() {
        return snapshot;
    }
}