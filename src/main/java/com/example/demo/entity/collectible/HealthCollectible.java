package com.example.demo.entity.collectible;

import com.example.demo.service.ServiceLocator;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.Image;

public class HealthCollectible extends Collectible {
    private static final Image IMAGE = ImageUtils.getImageFromName("health_collectible.png");

    public HealthCollectible() {
        super(IMAGE);
    }

    @Override
    protected void onCollected() {
        getPlayer().resetHealth();
    }
}
