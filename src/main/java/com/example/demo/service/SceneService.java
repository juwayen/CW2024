package com.example.demo.service;

import com.example.demo.Background;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

public class SceneService {
    private final Scene scene;
    private final Group root;
    private final Group bottomLayer;
    private final Group middleLayer;
    private final Group topLayer;

    public Scene getScene() {
        return scene;
    }

    public SceneService(Scene scene) {
        this.scene = scene;
        this.root = (Group) scene.getRoot();
        this.bottomLayer = new Group();
        this.middleLayer = new Group();
        this.topLayer = new Group();

        initialize();
    }

    private void initialize() {
        initializeRoot();
        initializeBackground();
    }

    private void initializeRoot() {
        root.getChildren().add(bottomLayer);
        root.getChildren().add(middleLayer);
        root.getChildren().add(topLayer);
    }

    public void initializeBackground() {
        bottomLayer.getChildren().add(new Background());
    }

    public void addNodeToTopLayer(Node node) {
        topLayer.getChildren().add(node);
    }

    public void clearTopLayer() {
        topLayer.getChildren().clear();
    }

    public void addNodeToMiddleLayer(Node node) {
        middleLayer.getChildren().add(node);
    }

    public void removeNodeFromMiddleLayer(Node node) {
        middleLayer.getChildren().remove(node);
    }

    public void clearMiddleLayer() {
        middleLayer.getChildren().clear();
    }
}
