package com.example.demo.factory;

import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.entity.plane.PlaneData;
import com.example.demo.util.Vector;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Abstract factory class for creating {@link EnemyPlane}s in a specified formation.
 */
public abstract class FormationFactory implements Factory {
    private static final Vector ENEMY_MIN_POSITION = new Vector(64.0, 182.0);
    private static final Vector ENEMY_MAX_POSITION = new Vector(960.0, 428.5);

    private final PlaneData enemyPlaneData;
    private final Vector offsetToBottomMiddle;
    private final List<Vector> possiblePositions;
    private final Set<Vector> usedPositions;

    private int currentEnemyCount;
    private int totalEnemyCount;

    /**
     * Getter method for the current number of active enemies.
     *
     * @return The current count of active enemies.
     */
    public int getCurrentEnemyCount() {
        return currentEnemyCount;
    }

    /**
     * Getter method for the total count of enemies that have been created.
     *
     * @return The total number of enemies created.
     */
    public int getTotalEnemyCount() {
        return totalEnemyCount;
    }

    /**
     * Constructs a {@link FormationFactory} instance with the given {@link PlaneData} and vertical offset.
     *
     * @param enemyPlaneData The data representing the {@link EnemyPlane}.
     * @param yOffset The vertical offset of the formation.
     */
    public FormationFactory(PlaneData enemyPlaneData, double yOffset) {
        this.enemyPlaneData = enemyPlaneData;
        Image enemyImage = enemyPlaneData.getMovingStraightImages().get(0);
        this.offsetToBottomMiddle = new Vector(enemyImage.getWidth() / 2, enemyImage.getHeight() - yOffset);
        this.possiblePositions = new ArrayList<>();
        this.usedPositions = new HashSet<>();

        this.currentEnemyCount = 0;

        initializePossiblePositions(possiblePositions);
    }

    /**
     * Initializes the {@link List} of possible positions for the formation.
     *
     * @param possiblePositions The {@link List} of {@link Vector} positions to be populated with potential positions.
     */
    protected abstract void initializePossiblePositions(List<Vector> possiblePositions);

    /**
     * Creates and initializes a new instance of an {@link EnemyPlane}, with a position from the available positions.
     * If no position is available, it randomizes the position.
     *
     * @return A newly created {@link EnemyPlane} instance added to the scene, initialized with the required configurations.
     */
    @Override
    public EnemyPlane create() {
        double initialX = Math.random() * ENEMY_MAX_POSITION.getX();

        Vector finalPos = getNextEnemyFinalPosition();

        if (finalPos == null)
            finalPos = getRandomFinalPosition();

        PlaneData currentPlaneData = enemyPlaneData.cloneObject();

        currentPlaneData.getInitialPosition().setX(initialX);
        currentPlaneData.setFinalPosition(finalPos.subtract(offsetToBottomMiddle));

        EnemyPlane enemyPlane = new EnemyPlane(currentPlaneData);
        enemyPlane.addToScene();

        currentEnemyCount++;
        totalEnemyCount++;

        Vector finalPosTemp = finalPos;
        enemyPlane.getRemovedSignal().connect(() -> currentEnemyCount--);
        enemyPlane.getDestroyedSignal().connect(() -> freePosition(finalPosTemp));

        return enemyPlane;
    }

    /**
     * Determines the next available {@link Vector} position from the list of possible positions.
     *
     * @return The next available {@link Vector} position, or {@code null} if no positions are available.
     */
    private Vector getNextEnemyFinalPosition() {
        for (Vector position : possiblePositions) {
            if (!usedPositions.contains(position)) {
                usedPositions.add(position);
                return position;
            }
        }

        return null;
    }

    /**
     * Generates a random position within the defined bounds using {@link Vector#random(Vector, Vector)}.
     *
     * @return A {@link Vector} representing the randomly selected position.
     */
    private Vector getRandomFinalPosition() {
        return Vector.random(ENEMY_MIN_POSITION, ENEMY_MAX_POSITION);
    }

    /**
     * Frees a specific position by removing it from the list of used positions.
     *
     * @param position The {@link Vector} representing the position to be freed.
     */
    private void freePosition(Vector position) {
        usedPositions.remove(position);
    }
}
