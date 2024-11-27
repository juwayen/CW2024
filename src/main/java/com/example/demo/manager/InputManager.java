package com.example.demo.manager;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class InputManager {
    private static InputManager instance;

    private final KeyCode[] moveUpKeys = {KeyCode.UP, KeyCode.W};
    private final KeyCode[] moveDownKeys = {KeyCode.DOWN, KeyCode.S};
    private final KeyCode[] fireKeys = {KeyCode.SPACE};

    private final Set<KeyCode> activeKeys = new HashSet<>();

    private Scene scene;

    private InputManager() {}

    public static InputManager getInstance() {
        if (instance == null)
            instance = new InputManager();

        return instance;
    }

    public void setScene(Scene newScene) {
        this.scene = newScene;
        initialize();
    }

    private void initialize() {
        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
    }

    private boolean isActionActive(KeyCode[] actionKeys) {
        for (KeyCode keyCode : actionKeys) {
            if (activeKeys.contains(keyCode))
                return true;
        }

        return false;
    }

    public boolean isMoveUpActive() {
        return isActionActive(moveUpKeys);
    }

    public boolean isMoveDownActive() {
        return isActionActive(moveDownKeys);
    }

    public boolean isFireActive() {
        return isActionActive(fireKeys);
    }
}