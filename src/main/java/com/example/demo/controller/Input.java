package com.example.demo.controller;

import com.example.demo.util.Vector;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

public class Input {
    private static final Set<KeyCode> activeKeys = new HashSet<>();
    private static final KeyCode[] moveUpKeys = {KeyCode.UP, KeyCode.W};
    private static final KeyCode[] moveDownKeys = {KeyCode.DOWN, KeyCode.S};
    private static final KeyCode[] moveLeftKeys = {KeyCode.LEFT, KeyCode.A};
    private static final KeyCode[] moveRightKeys = {KeyCode.RIGHT, KeyCode.D};
    private static final KeyCode[] fireKeys = {KeyCode.SPACE};
    private static final Vector inputMoveDirection = new Vector();

    public static void initialize(Scene scene) {
        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
    }

    private static boolean isActionActive(KeyCode[] actionKeys) {
        for (KeyCode key : actionKeys) {
            if (activeKeys.contains(key))
                return true;
        }

        return false;
    }

    public static boolean isMoveUpActive() {
        return isActionActive(moveUpKeys);
    }

    public static boolean isMoveDownActive() {
        return isActionActive(moveDownKeys);
    }

    public static boolean isMoveLeftActive() {
        return isActionActive(moveLeftKeys);
    }

    public static boolean isMoveRightActive() {
        return isActionActive(moveRightKeys);
    }

    public static boolean isFireActive() {
        return isActionActive(fireKeys);
    }

    public static Vector getInputMoveDirection() {
        double hPosComponent = isMoveRightActive() ? 1 : 0;
        double hNegComponent = isMoveLeftActive() ? 1 : 0;
        double vPosComponent = isMoveDownActive() ? 1 : 0;
        double vNegComponent = isMoveUpActive() ? 1 : 0;

        inputMoveDirection.setX(hPosComponent - hNegComponent);
        inputMoveDirection.setY(vPosComponent - vNegComponent);
        inputMoveDirection.normalize();

        return inputMoveDirection;
    }
}