package com.example.demo.factory;

import com.example.demo.Controller;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.entity.plane.PlaneData;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class FormationFactory implements Factory {
    private static final Vector ENEMY_MIN_POSITION = new Vector(64.0, 182.0);
    private static final Vector ENEMY_MAX_POSITION = new Vector(960.0, 428.5);

    private final Controller controller;
    private final PlaneData enemyPlaneData;
    private final Vector offsetToBottomMiddle;
    private final List<Vector> possiblePositions;
    private final Set<Vector> usedPositions;

    private int currentEnemyCount;
    private int totalEnemyCount;

    public int getCurrentEnemyCount() {
        return currentEnemyCount;
    }

    public int getTotalEnemyCount() {
        return totalEnemyCount;
    }

    public FormationFactory(Controller controller, PlaneData enemyPlaneData, double yOffset) {
        this.controller = controller;
        this.enemyPlaneData = enemyPlaneData;
        Image enemyImage = enemyPlaneData.getMovingStraightImages().get(0);
        this.offsetToBottomMiddle = new Vector(enemyImage.getWidth() / 2, enemyImage.getHeight() - yOffset);
        this.possiblePositions = new ArrayList<>();
        this.usedPositions = new HashSet<>();

        this.currentEnemyCount = 0;

        initializePossiblePositions(possiblePositions);
    }

    protected abstract void initializePossiblePositions(List<Vector> possiblePositions);

    @Override
    public EnemyPlane create() {
        double initialX = Math.random() * ENEMY_MAX_POSITION.getX();

        Vector finalPos = getNextEnemyFinalPosition();

        if (finalPos == null)
            finalPos = getRandomFinalPosition();

        PlaneData currentPlaneData = enemyPlaneData.cloneObject();

        currentPlaneData.getInitialPosition().setX(initialX);
        currentPlaneData.setFinalPosition(finalPos.subtract(offsetToBottomMiddle));

        EnemyPlane enemyPlane = new EnemyPlane(controller, currentPlaneData);
        enemyPlane.addToScene();

        currentEnemyCount++;
        totalEnemyCount++;

        Vector finalPosTemp = finalPos;
        enemyPlane.getRemovedSignal().connect(() -> currentEnemyCount--);
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
