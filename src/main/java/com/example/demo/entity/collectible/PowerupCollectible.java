package com.example.demo.entity.collectible;

import com.example.demo.Controller;
import com.example.demo.util.ImageUtils;
import javafx.scene.image.Image;

public class PowerupCollectible extends Collectible {
    private static final Image IMAGE = ImageUtils.getImageFromName("powerup_collectible.png");

    private final Controller controller;

    public PowerupCollectible(Controller controller) {
        super(controller, IMAGE);

        this.controller = controller;
    }

    @Override
    protected void onCollected() {
        controller.getPlayer().powerup();
    }
}
