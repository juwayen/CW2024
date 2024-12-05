package com.example.demo.service;

import com.example.demo.util.Vector;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class InputService {
    private static final KeyCode[] moveUpKeys = {KeyCode.UP, KeyCode.W};
    private static final KeyCode[] moveDownKeys = {KeyCode.DOWN, KeyCode.S};
    private static final KeyCode[] moveLeftKeys = {KeyCode.LEFT, KeyCode.A};
    private static final KeyCode[] moveRightKeys = {KeyCode.RIGHT, KeyCode.D};
    private static final KeyCode[] fireKeys = {KeyCode.SPACE};

    private final Set<KeyCode> activeKeys;
    private final Vector inputMoveDirection;

    public InputService() {
        this.activeKeys = new HashSet<>();
        this.inputMoveDirection = new Vector();

        initialize();
    }

    private void initialize() {
        SceneService sceneService = ServiceLocator.getSceneService();
        Scene scene = sceneService.getScene();

        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
    }

    private boolean isActionActive(KeyCode[] actionKeys) {
        for (KeyCode key : actionKeys) {
            if (activeKeys.contains(key))
                return true;
        }

        return false;
    }

    public boolean isAnyKeyActive() {
        return !activeKeys.isEmpty();
    }

    public boolean isMoveUpActive() {
        return isActionActive(moveUpKeys);
    }

    public boolean isMoveDownActive() {
        return isActionActive(moveDownKeys);
    }

    public boolean isMoveLeftActive() {
        return isActionActive(moveLeftKeys);
    }

    public boolean isMoveRightActive() {
        return isActionActive(moveRightKeys);
    }

    public boolean isFireActive() {
        return isActionActive(fireKeys);
    }

    public Vector getInputMoveDirection() {
        double hPosComponent = isMoveRightActive() ? 1 : 0;
        double hNegComponent = isMoveLeftActive() ? 1 : 0;
        double vPosComponent = isMoveDownActive() ? 1 : 0;
        double vNegComponent = isMoveUpActive() ? 1 : 0;

        inputMoveDirection.setX(hPosComponent - hNegComponent);
        inputMoveDirection.setY(vPosComponent - vNegComponent);

        return inputMoveDirection.normalized();
    }
}