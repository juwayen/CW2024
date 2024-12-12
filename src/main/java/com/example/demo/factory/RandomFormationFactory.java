package com.example.demo.factory;

import com.example.demo.entity.plane.PlaneData;
import com.example.demo.util.Vector;

import java.util.List;

/**
 * A concrete implementation of the {@link FormationFactory} class to generate random positions.
 */
public class RandomFormationFactory extends FormationFactory {
    /**
     * Constructs a {@link RandomFormationFactory} instance.
     *
     * @param enemyPlaneData The {@link PlaneData} representing the {@link com.example.demo.entity.plane.EnemyPlane}.
     */
    public RandomFormationFactory(PlaneData enemyPlaneData) {
        super(enemyPlaneData, 0.0);
    }

    /**
     * Leaves the available positions empty so the {@link FormationFactory} always uses a random position.
     *
     * @param possiblePositions The {@link List} to populate with the possible {@link Vector} positions.
     */
    @Override
    protected void initializePossiblePositions(List<Vector> possiblePositions) {}
}
