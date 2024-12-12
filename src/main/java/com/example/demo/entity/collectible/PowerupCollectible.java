package com.example.demo.entity.collectible;

import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.Image;

/**
 * Concrete implementation of {@link Collectible} representing an item that powers up the player.
 */
public class PowerupCollectible extends Collectible {
    private static final Image IMAGE = ImageUtils.getImageFromName("powerup_collectible.png");

    /**
     * Constructs a new {@link PowerupCollectible} instance, initializing it with a predefined image.
     */
    public PowerupCollectible() {
        super(IMAGE);
    }

    /**
     * Invokes {@link PlayerPlane#powerup()}.
     */
    @Override
    protected void onCollected() {
        getPlayer().powerup();
    }
}
