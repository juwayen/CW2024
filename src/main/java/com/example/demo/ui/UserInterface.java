package com.example.demo.ui;

import com.example.demo.Controller;
import com.example.demo.entity.plane.PlayerPlane;
import javafx.scene.Group;

public class UserInterface extends Group {
    private static final double HEALTH_DISPLAY_X_POSITION = 8;
    private static final double HEALTH_DISPLAY_Y_POSITION = 8;

    private final Controller controller;
    private final PlayerPlane player;
    private final HealthDisplay healthDisplay;

    public UserInterface(Controller controller) {
        this.controller = controller;
        this.player = controller.getPlayer();
        this.healthDisplay = new HealthDisplay(HEALTH_DISPLAY_X_POSITION, HEALTH_DISPLAY_Y_POSITION, player.getHealth());

        initialize();
    }

    private void initialize() {
        initializeHealthDisplay();
        connectSignals();
    }

    private void initializeHealthDisplay() {
        getChildren().add(healthDisplay);
    }

    private void connectSignals() {
        player.getHealthUpdatedSignal().connect(this::onPlayerHealthUpdated);
        controller.getSceneResetSignal().connect(this::onSceneReset);
    }

    private void onPlayerHealthUpdated() {
        healthDisplay.setHealth(player.getHealth());
    }

    private void onSceneReset() {
        healthDisplay.reset();
    }
}
