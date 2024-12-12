package com.example.demo.entity.state;

import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.Plane;

/**
 * Represents the {@link EntityState} of a {@link Plane} when it has been destroyed.
 */
public class PlaneDestroyedState implements EntityState {
    /**
     * Plays the destroyed animation when this {@link EntityState} is entered.
     *
     * @param entity The {@link Entity} instance transitioning into this {@link EntityState}.
     */
    @Override
    public void enter(Entity entity) {
        if (!(entity instanceof Plane plane))
            return;

        plane.getDestroyedTimeline().play();
    }

    /**
     * Stops the destroyed animation when this {@link EntityState} is exited.
     *
     * @param entity The {@link Entity} instance transitioning into this {@link EntityState}.
     */
    @Override
    public void exit(Entity entity) {
        if (!(entity instanceof Plane plane))
            return;

        plane.getDestroyedTimeline().stop();
    }
}
