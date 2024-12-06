package com.example.demo.ui;

import com.example.demo.GameController;
import com.example.demo.entity.plane.PlayerPlane;
import javafx.scene.Group;

public class UserInterface extends Group {
    private static final double HEART_DISPLAY_X_POSITION = 5;
    private static final double HEART_DISPLAY_Y_POSITION = 25;

    private final GameController gameController;
    private final PlayerPlane player;
    private final HeartDisplay heartDisplay;

    public UserInterface(GameController gameController) {
        this.gameController = gameController;
        this.player = gameController.getPlayer();
        this.heartDisplay = new HeartDisplay(HEART_DISPLAY_X_POSITION, HEART_DISPLAY_Y_POSITION, player.getHealth());

        initialize();
    }

    private void initialize() {
        initializeHeartDisplay();
        connectSignals();
    }

    private void initializeHeartDisplay() {
        getChildren().add(heartDisplay);
    }

    private void connectSignals() {
        player.getDamageTakenSignal().connect(this::onPlayerDamageTaken);
        gameController.getSceneResetSignal().connect(this::onSceneReset);
    }

    private void onPlayerDamageTaken() {
        heartDisplay.setHeartCount(player.getHealth());
    }

    private void onSceneReset() {
        heartDisplay.reset();
    }
}
