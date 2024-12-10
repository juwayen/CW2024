package com.example.demo.entity.collectible;

import com.example.demo.Controller;
import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.service.Collidable;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

public abstract class Collectible extends Entity {
    private static final double MAX_TIME_MILLISECONDS = 3000;

    private boolean isActive;
    private double millisecondsSinceSpawned;

    public Collectible(Controller controller, Image image) {
        super(controller, image, new Vector());

        this.isActive = false;
        this.millisecondsSinceSpawned = 0;
    }

    @Override
    public void update() {
        if (!isActive)
            return;

        millisecondsSinceSpawned += MILLISECOND_DELAY;

        if (millisecondsSinceSpawned > MAX_TIME_MILLISECONDS)
            despawn();
    }

    @Override
    public void onCollision(Collidable collidable) {
        if (!(collidable instanceof PlayerPlane))
            return;

        onCollected();
        despawn();
    }

    protected abstract void onCollected();

    public void spawnAt(Vector position) {
        setPosition(position);
        addToScene();

        isActive = true;
        millisecondsSinceSpawned = 0;
    }

    private void despawn() {
        removeFromScene();

        isActive = false;
    }
}
