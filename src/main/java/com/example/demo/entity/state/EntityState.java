package com.example.demo.entity.state;

import com.example.demo.entity.Entity;

/**
 * Represents a state that an {@link Entity} can exist in within the context of a state machine.
 */
public interface EntityState {
    /**
     * Defines the actions when an {@link Entity} enters this specific state.
     *
     * @param entity The {@link Entity} instance that is transitioning into this state.
     */
    void enter(Entity entity);

    /**
     * Defines the actions when an {@link Entity} exits this specific state.
     *
     * @param entity The {@link Entity} instance that is transitioning out of this state.
     */
    void exit(Entity entity);
}
