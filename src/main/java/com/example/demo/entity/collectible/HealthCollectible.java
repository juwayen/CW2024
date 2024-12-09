package com.example.demo.entity.collectible;

import com.example.demo.Controller;
import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.Image;

public class HealthCollectible extends Collectible {
    private static final Image IMAGE = ImageUtils.getImageFromName("health_collectible.png");

    private final Controller controller;

    public HealthCollectible(Controller controller) {
        super(controller, IMAGE);

        this.controller = controller;
    }

    @Override
    protected void onCollected() {
        controller.getPlayer().setHealth(PlayerPlane.INITIAL_HEALTH);
    }
}
