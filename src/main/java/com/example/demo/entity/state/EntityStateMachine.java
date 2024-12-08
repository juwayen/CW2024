package com.example.demo.entity.state;

import com.example.demo.entity.Entity;

public class EntityStateMachine {
    private final Entity entity;

    private EntityState currentEntityState;

    public EntityStateMachine(Entity entity) {
        this.entity = entity;
    }

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
