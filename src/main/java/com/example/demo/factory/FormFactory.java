package com.example.demo.factory;

import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FormFactory implements Factory {
    private static final Vector ENEMY_MIN_POSITION = new Vector(0.0, 64.0);
    private static final Vector ENEMY_MAX_POSITION = new Vector(928.0, 364.5);

    private final EnemyPlaneFactory enemyPlaneFactory;
    private final List<Vector> possiblePositions;
    private final Set<Vector> usedPositions;

    public FormFactory(EnemyPlaneFactory enemyPlaneFactory) {
        this.enemyPlaneFactory = enemyPlaneFactory;
        this.possiblePositions = new ArrayList<>();
        this.usedPositions = new HashSet<>();

        initializePossiblePositions(possiblePositions);
    }

    protected abstract void initializePossiblePositions(List<Vector> possiblePositions);

    @Override
    public EnemyPlane create() {
        double initialX = Math.random() * ENEMY_MAX_POSITION.getX();

        Vector finalPos = getNextEnemyFinalPosition();

        if (finalPos == null)
            finalPos = getRandomFinalPosition();

        enemyPlaneFactory.setInitialX(initialX);
        enemyPlaneFactory.setFinalPosition(finalPos);
        EnemyPlane enemyPlane = enemyPlaneFactory.create();
        enemyPlane.addToScene();

        Vector finalPosTemp = finalPos;
        enemyPlane.getDestroyedSignal().connect(() -> freePosition(finalPosTemp));

        return enemyPlane;
    }

    private Vector getNextEnemyFinalPosition() {
        for (Vector position : possiblePositions) {
            if (!usedPositions.contains(position)) {
                usedPositions.add(position);
                return position;
            }
        }

        return null;
    }

    private Vector getRandomFinalPosition() {
        return Vector.random(ENEMY_MIN_POSITION, ENEMY_MAX_POSITION);
    }

    private void freePosition(Vector position) {
        usedPositions.remove(position);
    }
}
