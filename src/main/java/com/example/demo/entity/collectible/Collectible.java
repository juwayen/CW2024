package com.example.demo.entity.collectible;

import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.service.AudioService;
import com.example.demo.service.Collidable;
import com.example.demo.service.ServiceLocator;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

public abstract class Collectible extends Entity {
    private static final double MAX_TIME_MILLISECONDS = 3000;

    private final PlayerPlane player;
    private final AudioService audioService;

    private boolean isActive;
    private double millisecondsSinceSpawned;

    protected PlayerPlane getPlayer() {
        return player;
    }

    public Collectible(Image image) {
        super(image, new Vector());

        this.player = ServiceLocator.getGameService().getPlayer();
        this.audioService = ServiceLocator.getAudioService();

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
        audioService.playSound(AudioService.Sound.COLLECTIBLE_COLLECTED);
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
