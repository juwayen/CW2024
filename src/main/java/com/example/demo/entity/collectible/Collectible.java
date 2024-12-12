package com.example.demo.entity.collectible;

import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.PlayerPlane;
import com.example.demo.service.AudioService;
import com.example.demo.service.Collidable;
import com.example.demo.service.ServiceLocator;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

/**
 * Abstract base class for collectible items in the game.
 */
public abstract class Collectible extends Entity {
    private static final double MAX_TIME_MILLISECONDS = 3000;

    private final PlayerPlane player;
    private final AudioService audioService;

    private boolean isActive;
    private double millisecondsSinceSpawned;

    /**
     * Getter method for the {@link PlayerPlane} instance.
     *
     * @return The {@link PlayerPlane} instance.
     */
    protected PlayerPlane getPlayer() {
        return player;
    }

    /**
     * Constructs a new {@link Collectible} instance, initializing it with an image.
     *
     * @param image The {@link Image} used for the visual representation.
     */
    public Collectible(Image image) {
        super(image, new Vector());

        this.player = ServiceLocator.getGameService().getPlayer();
        this.audioService = ServiceLocator.getAudioService();

        this.isActive = false;
        this.millisecondsSinceSpawned = 0;
    }

    /**
     * Update method to check if the entity should despawn after the specified time.
     */
    @Override
    public void update() {
        if (!isActive)
            return;

        millisecondsSinceSpawned += MILLISECOND_DELAY;

        if (millisecondsSinceSpawned > MAX_TIME_MILLISECONDS)
            despawn();
    }

    /**
     * Handles the logic for collision with the {@link PlayerPlane}.
     */
    @Override
    public void onCollision(Collidable collidable) {
        if (!(collidable instanceof PlayerPlane))
            return;

        onCollected();
        despawn();
        audioService.playSound(AudioService.Sound.COLLECTIBLE_COLLECTED);
    }

    /**
     * Abstract method to define the logic executed when it is collected.
     */
    protected abstract void onCollected();

    /**
     * Spawns at the specified position, making it active and adding it to the scene.
     *
     * @param position The {@link Vector} representing the position where it should be spawned.
     */
    public void spawnAt(Vector position) {
        setPosition(position);
        addToScene();

        isActive = true;
        millisecondsSinceSpawned = 0;
    }

    /**
     * Terminates by removing it from the scene and deactivating it.
     */
    private void despawn() {
        removeFromScene();

        isActive = false;
    }
}
