package com.example.demo.entity.collectible;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.Image;

/**
 * Concrete implementation of {@link Collectible} representing an item that restores the player's health.
 */
public class HealthCollectible extends Collectible {
    private static final Image IMAGE = ImageUtils.getImageFromName("health_collectible.png");

    /**
     * Constructs a new {@link HealthCollectible} instance, initializing it with a predefined image.
     */
    public HealthCollectible() {
        super(IMAGE);
    }

    /**
     * Invokes {@link PlayerPlane#resetHealth()}.
     */
    @Override
    protected void onCollected() {
        getPlayer().resetHealth();
    }
}
