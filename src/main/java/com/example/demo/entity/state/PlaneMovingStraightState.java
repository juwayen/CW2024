package com.example.demo.entity.state;

import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.Plane;

/**
 * Represents the {@link EntityState} of a {@link Plane} when it is moving up or down.
 */
public class PlaneMovingStraightState implements EntityState {
    /**
     * Plays the moving straight animation when this {@link EntityState} is entered.
     *
     * @param entity The {@link Entity} instance transitioning into this {@link EntityState}.
     */
    @Override
    public void enter(Entity entity) {
        if (!(entity instanceof Plane plane))
            return;

        plane.getMovingStraightTimeline().play();
    }

    /**
     * Stops the moving straight animation when this {@link EntityState} is exited.
     *
     * @param entity The {@link Entity} instance transitioning into this {@link EntityState}.
     */
    @Override
    public void exit(Entity entity) {
        if (!(entity instanceof Plane plane))
            return;

        plane.getMovingStraightTimeline().stop();
    }
}
