package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service responsible for handling collisions.
 */
public class CollisionService implements Updatable {
    private final List<Collidable> collidables;
    private final List<Collidable> collidablesToAdd;
    private final List<Collidable> collidablesToRemove;

    /**
     * Constructs a new {@link CollisionService} instance, initializing necessary {@link Collidable} {@link List}s.
     */
    public CollisionService() {
        this.collidables = new ArrayList<>();
        this.collidablesToAdd = new ArrayList<>();
        this.collidablesToRemove = new ArrayList<>();

        initializeUpdate();
    }

    /**
     * Initializes itself by registering itself to the {@link UpdateService}.*
     */
    private void initializeUpdate() {
        ServiceLocator.getUpdateService().addToLoop(this);
    }

    /**
     * Update method that invokes {@link #handleCollisions()} and {@link #processQueue()}.
     */
    @Override
    public void update() {
        handleCollisions();
        processQueue();
    }

    /**
     * Invokes {@link Collidable#onCollision(Collidable)} on any pair of overlapping {@link Collidable}s.
     */
    private void handleCollisions() {
        int collidablesSize = collidables.size();
        List<Collidable> collidablesCopy = new ArrayList<>(collidables);

        for (int i = 0; i < collidablesSize; i++) {
            for (int j = i + 1; j < collidablesSize; j++) {
                Collidable collidable1 = collidablesCopy.get(i);
                Collidable collidable2 = collidablesCopy.get(j);

                if (collidablesToRemove.contains(collidable1) || collidablesToRemove.contains(collidable2))
                    continue;

                if (collidable1.getHitbox().intersects(collidable2.getHitbox())) {
                    collidable1.onCollision(collidable2);
                    collidable2.onCollision(collidable1);
                }
            }
        }
    }

    /**
     * Processes the queues of {@link Collidable}s to add and remove from collision detection.
     */
    private void processQueue() {
        collidables.addAll(collidablesToAdd);
        collidablesToAdd.clear();

        collidables.removeAll(collidablesToRemove);
        collidablesToRemove.clear();
    }

    /**
     * Enables collision detection for the specified {@link Collidable}.
     *
     * @param collidable The {@link Collidable} to enable collision detection for
     */
    public void enableCollision(Collidable collidable) {
        collidablesToAdd.add(collidable);
    }

    /**
     * Disables collision detection for the specified {@link Collidable}.
     *
     * @param collidable The {@link Collidable} to disable collision detection for
     */
    public void disableCollision(Collidable collidable) {
        collidablesToRemove.add(collidable);
    }
}
