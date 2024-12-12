package com.example.demo.factory;

import com.example.demo.entity.plane.PlaneData;
import com.example.demo.util.Vector;

import java.util.List;

/**
 * A concrete implementation of the {@link FormationFactory} class to generate positions in a straight line.
 */
public class WallFormationFactory extends FormationFactory {
    private static final double POSITION_Y = 128.0;
    private static final Vector POSSIBLE_POSITION_1 = new Vector(512.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_2 = new Vector(384.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_3 = new Vector(640.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_4 = new Vector(256.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_5 = new Vector(768.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_6 = new Vector(128.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_7 = new Vector(896.0, POSITION_Y);

    /**
     * Constructs a {@link WallFormationFactory} instance.
     *
     * @param enemyPlaneData The {@link PlaneData} representing the {@link com.example.demo.entity.plane.EnemyPlane}.
     */
    public WallFormationFactory(PlaneData enemyPlaneData, double yOffset) {
        super(enemyPlaneData, yOffset);
    }

    /**
     * Populates the provided list with predefined possible positions.
     *
     * @param possiblePositions The {@link List} to populate with the possible {@link Vector} positions.
     */
    @Override
    protected void initializePossiblePositions(List<Vector> possiblePositions) {
        possiblePositions.add(POSSIBLE_POSITION_1);
        possiblePositions.add(POSSIBLE_POSITION_2);
        possiblePositions.add(POSSIBLE_POSITION_3);
        possiblePositions.add(POSSIBLE_POSITION_4);
        possiblePositions.add(POSSIBLE_POSITION_5);
        possiblePositions.add(POSSIBLE_POSITION_6);
        possiblePositions.add(POSSIBLE_POSITION_7);
    }
}
