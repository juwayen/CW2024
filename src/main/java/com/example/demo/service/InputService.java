package com.example.demo.service;

import com.example.demo.util.Vector;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

/**
 * Service responsible for processing user input.
 */
public class InputService {
    private static final KeyCode[] moveUpKeys = {KeyCode.UP, KeyCode.W};
    private static final KeyCode[] moveDownKeys = {KeyCode.DOWN, KeyCode.S};
    private static final KeyCode[] moveLeftKeys = {KeyCode.LEFT, KeyCode.A};
    private static final KeyCode[] moveRightKeys = {KeyCode.RIGHT, KeyCode.D};
    private static final KeyCode[] fireKeys = {KeyCode.SPACE};

    private final Set<KeyCode> activeKeys;
    private final Vector inputMoveDirection;

    /**
     * Returns the direction {@link Vector} based on active input keys.
     *
     * @return A normalized {@link Vector} representing the direction of movement.
     */
    public Vector getInputMoveDirection() {
        double hPosComponent = isMoveRightActive() ? 1 : 0;
        double hNegComponent = isMoveLeftActive() ? 1 : 0;
        double vPosComponent = isMoveDownActive() ? 1 : 0;
        double vNegComponent = isMoveUpActive() ? 1 : 0;

        inputMoveDirection.setX(hPosComponent - hNegComponent);
        inputMoveDirection.setY(vPosComponent - vNegComponent);

        return inputMoveDirection.normalized();
    }

    /**
     * Initializes the internal key-tracking structure.
     */
    public InputService() {
        this.activeKeys = new HashSet<>();
        this.inputMoveDirection = new Vector();

        initialize();
    }

    /**
     * Initializes event handlers for key press and key release events on the application's primary scene.
     */
    private void initialize() {
        Scene scene = ServiceLocator.getSceneService().getScene();

        scene.setOnKeyPressed(event -> activeKeys.add(event.getCode()));
        scene.setOnKeyReleased(event -> activeKeys.remove(event.getCode()));
    }

    /**
     * Checks if any of the specified keys are currently active.
     *
     * @param actionKeys an array of {@link KeyCode} representing the keys to check for activity.
     * @return {@code true} if at least one of the specified keys is active, {@code false} otherwise.
     */
    private boolean isActionActive(KeyCode[] actionKeys) {
        for (KeyCode key : actionKeys) {
            if (activeKeys.contains(key))
                return true;
        }

        return false;
    }

    /**
     * Checks if any key is currently active.
     *
     * @return {@code true} if at least one key is active, {@code false} otherwise.
     */
    public boolean isAnyKeyActive() {
        return !activeKeys.isEmpty();
    }

    /**
     * Determines whether the "move up" action is currently active.
     *
     * @return {@code true} if any of the keys mapped to the "move up" action are currently active, {@code false} otherwise.
     */
    public boolean isMoveUpActive() {
        return isActionActive(moveUpKeys);
    }

    /**
     * Determines whether the "move down" action is currently active.
     *
     * @return {@code true} if any of the keys mapped to the "move down" action are currently active, {@code false} otherwise.
     */
    public boolean isMoveDownActive() {
        return isActionActive(moveDownKeys);
    }

    /**
     * Determines whether the "move left" action is currently active.
     *
     * @return {@code true} if any of the keys mapped to the "move left" action are currently active, {@code false} otherwise.
     */
    public boolean isMoveLeftActive() {
        return isActionActive(moveLeftKeys);
    }

    /**
     * Determines whether the "move right" action is currently active.
     *
     * @return {@code true} if any of the keys mapped to the "move right" action are currently active, {@code false} otherwise.
     */
    public boolean isMoveRightActive() {
        return isActionActive(moveRightKeys);
    }

    /**
     * Determines whether the "fire" action is currently active.
     *
     * @return {@code true} if any of the keys mapped to the "fire" action are currently active, {@code false} otherwise.
     */
    public boolean isFireActive() {
        return isActionActive(fireKeys);
    }
}