package com.example.demo.entity.collectible;

import com.example.demo.service.ServiceLocator;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.Image;

public class PowerupCollectible extends Collectible {
    private static final Image IMAGE = ImageUtils.getImageFromName("powerup_collectible.png");

    public PowerupCollectible() {
        super(IMAGE);
    }

    @Override
    protected void onCollected() {
        getPlayer().powerup();
    }
}
