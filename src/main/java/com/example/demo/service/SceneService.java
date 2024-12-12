package com.example.demo.service;

import com.example.demo.ui.Background;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * Service responsible for managing and organizing a {@link Scene} with layers.
 */
public class SceneService {
    private final Scene scene;
    private final Group root;
    private final Group bottomLayer;
    private final Group middleLayer;
    private final Group topLayer;

    /**
     * Retrieves the {@link Scene} managed by this service.
     *
     * @return The {@link Scene} instance currently being managed.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Constructs an instance of SceneService.
     * Initializes layers for the scene and sets up the root structure.
     *
     * @param scene The JavaFX {@link Scene} to be managed by this service.
     */
    public SceneService(Scene scene) {
        this.scene = scene;
        this.root = (Group) scene.getRoot();
        this.bottomLayer = new Group();
        this.middleLayer = new Group();
        this.topLayer = new Group();

        initializeRoot();
        initializeBackground();
    }

    /**
     * Initializes the root by adding the predefined layer groups (bottom, middle, and top layers).
     */
    private void initializeRoot() {
        root.getChildren().add(bottomLayer);
        root.getChildren().add(middleLayer);
        root.getChildren().add(topLayer);
    }

    /**
     * Initializes the bottom layer for the scene by adding a {@link Background} instance to the bottom layer.
     */
    public void initializeBackground() {
        bottomLayer.getChildren().add(new Background());
    }

    /**
     * Adds the specified {@link Node} to the middle layer of the scene.
     *
     * @param node The {@link Node} to be added to the middle layer.
     */
    public void addNodeToMiddleLayer(Node node) {
        middleLayer.getChildren().add(node);
    }

    /**
     * Removes the specified {@link Node} to the middle layer of the scene.
     *
     * @param node The {@link Node} to be removed from the middle layer.
     */
    public void removeNodeFromMiddleLayer(Node node) {
        middleLayer.getChildren().remove(node);
    }

    /**
     * Clears all elements from the middle layer of the scene.
     */
    public void clearMiddleLayer() {
        middleLayer.getChildren().clear();
    }

    /**
     * Adds the specified {@link Node} to the top layer of the scene.
     *
     * @param node The {@link Node} to be added to the top layer.
     */
    public void addNodeToTopLayer(Node node) {
        topLayer.getChildren().add(node);
    }

    /**
     * Clears all elements from the top layer of the scene.
     */
    public void clearTopLayer() {
        topLayer.getChildren().clear();
    }
}
