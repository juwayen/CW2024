package com.example.demo.entity.state;

import com.example.demo.entity.Entity;

/**
 * Manages the {@link EntityState} transitions of an {@link Entity} within a state machine framework.
 */
public class EntityStateMachine {
    private final Entity entity;

    private EntityState currentEntityState;

    /**
     * Constructs an {@link EntityStateMachine} for managing state transitions of a specific {@link Entity} instance.
     *
     * @param entity The {@link Entity} whose state transitions will be managed.
     */
    public EntityStateMachine(Entity entity) {
        this.entity = entity;
    }

    /**
     * Changes the current {@link EntityState} of the {@link Entity} within the state machine to a new state.
     *
     * @param newEntityState The new {@link EntityState} to transition into.
     */
    public void changeState(EntityState newEntityState) {
        if (currentEntityState != null && currentEntityState.getClass() == newEntityState.getClass())
            return;

        if (currentEntityState != null)
            currentEntityState.exit(entity);

        if (newEntityState != null)
            newEntityState.enter(entity);

        currentEntityState = newEntityState;
    }
}
