package com.example.demo.entity.state;

import com.example.demo.entity.Entity;
import com.example.demo.entity.bullet.Bullet;

/**
 * Represents the {@link EntityState} of a {@link Bullet} when it has been destroyed.
 */
public class BulletMovingState implements EntityState {
    /**
     * Plays the moving animation when this {@link EntityState} is entered.
     *
     * @param entity The {@link Entity} instance transitioning into this {@link EntityState}.
     */
    @Override
    public void enter(Entity entity) {
        if (!(entity instanceof Bullet bullet))
            return;

        bullet.getMovingTimeline().play();
    }

    /**
     * Stops the moving animation when this {@link EntityState} is entered.
     *
     * @param entity The {@link Entity} instance transitioning into this {@link EntityState}.
     */
    @Override
    public void exit(Entity entity) {
        if (!(entity instanceof Bullet bullet))
            return;

        bullet.getMovingTimeline().stop();
    }
}
