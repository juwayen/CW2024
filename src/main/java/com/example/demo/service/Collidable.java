package com.example.demo.service;

import javafx.geometry.Bounds;

/**
 * Represents an object that can be involved in collision detection and handling.
 */
public interface Collidable {
    /**
     * Retrieves the {@link Bounds} (hitbox) of the object for collision detection.
     *
     * @return A {@link Bounds} object representing the object's collision area.
     */
    Bounds getHitbox();

    /**
     * Handles the collision logic when this object collides with another {@link Collidable} object.
     *
     * @param collidable The other {@link Collidable} object involved in the collision.
     */
    void onCollision(Collidable collidable);
}