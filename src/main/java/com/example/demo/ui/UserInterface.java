package com.example.demo.ui;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.service.GameService;
import com.example.demo.service.ServiceLocator;
import javafx.scene.Group;

/**
 * Responsible for managing and displaying in-game UI components.
 */
public class UserInterface extends Group {
    private static final double HEALTH_DISPLAY_X_POSITION = 8;
    private static final double HEALTH_DISPLAY_Y_POSITION = 8;

    private final GameService gameService;
    private final PlayerPlane player;
    private final HealthDisplay healthDisplay;

    /**
     * Constructs a new instance of the {@link UserInterface} class.
     * Initializes the in-game UI components and establishes connections to relevant {@link com.example.demo.util.Signal}s.
     */
    public UserInterface() {
        this.gameService = ServiceLocator.getGameService();
        this.player = gameService.getPlayer();
        this.healthDisplay = new HealthDisplay(HEALTH_DISPLAY_X_POSITION, HEALTH_DISPLAY_Y_POSITION, player.getHealth());

        initializeHealthDisplay();
        connectSignals();
    }

    /**
     * Adds the health display component to the collection of UI elements managed by this {@link UserInterface}.
     */
    private void initializeHealthDisplay() {
        getChildren().add(healthDisplay);
    }

    /**
     * Establishes signal connections for the {@link UserInterface} to respond to game events.
     * Connects the scene reset {@link com.example.demo.util.Signal} from {@link GameService} to the {@link #onSceneReset()} method.
     * Connects the health updated {@link com.example.demo.util.Signal} from {@link PlayerPlane} to the {@link #onPlayerHealthUpdated()} method.
     */
    private void connectSignals() {
        gameService.getSceneResetSignal().connect(this::onSceneReset);
        player.getHealthUpdatedSignal().connect(this::onPlayerHealthUpdated);
    }

    /**
     * Invokes the {@link HealthDisplay#reset()} method to restore the health bar to its full capacity.
     */
    private void onSceneReset() {
        healthDisplay.reset();
    }

    /**
     * Invokes the {@link HealthDisplay#setHealth(int)} method to reflect the player's current health.
     */
    private void onPlayerHealthUpdated() {
        healthDisplay.setHealth(player.getHealth());
    }
}
