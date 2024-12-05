package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

public class CollisionService implements Updatable {
    private final List<Collidable> collidables;
    private final List<Collidable> collidablesToAdd;
    private final List<Collidable> collidablesToRemove;

    public CollisionService() {
        this.collidables = new ArrayList<>();
        this.collidablesToAdd = new ArrayList<>();
        this.collidablesToRemove = new ArrayList<>();

        initialize();
    }

    private void initialize() {
        GameLoopService gameLoopService = ServiceLocator.getGameLoopService();
        gameLoopService.addToLoop(this);
    }

    @Override
    public void update() {
        handleCollisions();
        processQueue();
    }

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

    private void processQueue() {
        collidables.addAll(collidablesToAdd);
        collidablesToAdd.clear();

        collidables.removeAll(collidablesToRemove);
        collidablesToRemove.clear();
    }

    public void enableCollision(Collidable collidable) {
        collidablesToAdd.add(collidable);
    }

    public void disableCollision(Collidable collidable) {
        collidablesToRemove.add(collidable);
    }
}
