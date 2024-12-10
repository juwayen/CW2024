package com.example.demo.factory;

import com.example.demo.Controller;
import com.example.demo.entity.plane.PlaneData;
import com.example.demo.util.Vector;

import java.util.List;

public class WallFormationFactory extends FormationFactory {
    private static final double POSITION_Y = 128.0;
    private static final Vector POSSIBLE_POSITION_1 = new Vector(512.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_2 = new Vector(384.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_3 = new Vector(640.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_4 = new Vector(256.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_5 = new Vector(768.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_6 = new Vector(128.0, POSITION_Y);
    private static final Vector POSSIBLE_POSITION_7 = new Vector(896.0, POSITION_Y);

    public WallFormationFactory(Controller controller, PlaneData enemyPlaneData) {
        super(controller, enemyPlaneData, 0.0);
    }

    public WallFormationFactory(Controller controller, PlaneData enemyPlaneData, double yOffset) {
        super(controller, enemyPlaneData, yOffset);
    }

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
