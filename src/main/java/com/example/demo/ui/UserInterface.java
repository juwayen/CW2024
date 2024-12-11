package com.example.demo.ui;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.service.GameService;
import com.example.demo.service.ServiceLocator;
import javafx.scene.Group;

public class UserInterface extends Group {
    private static final double HEALTH_DISPLAY_X_POSITION = 8;
    private static final double HEALTH_DISPLAY_Y_POSITION = 8;

    private final GameService gameService;
    private final PlayerPlane player;
    private final HealthDisplay healthDisplay;

    public UserInterface() {
        this.gameService = ServiceLocator.getGameService();
        this.player = gameService.getPlayer();
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
        gameService.getSceneResetSignal().connect(this::onSceneReset);
        player.getHealthUpdatedSignal().connect(this::onPlayerHealthUpdated);
    }

    private void onSceneReset() {
        healthDisplay.reset();
    }

    private void onPlayerHealthUpdated() {
        healthDisplay.setHealth(player.getHealth());
    }
}
