package com.example.demo.controller;

import com.example.demo.entity.Collidable;
import com.example.demo.entity.Updatable;

import java.util.ArrayList;
import java.util.List;

public class CollisionEngine implements Updatable {
    private static CollisionEngine instance;

    private final List<Collidable> collidables = new ArrayList<>();
    private final List<Collidable> collidablesToAdd = new ArrayList<>();
    private final List<Collidable> collidablesToRemove = new ArrayList<>();

    private CollisionEngine() {
        addToGameLoop();
    }

    public static CollisionEngine getInstance() {
        if (instance == null)
            instance = new CollisionEngine();

        return instance;
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
