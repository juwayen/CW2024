package com.example.demo.factory;

import com.example.demo.Controller;
import com.example.demo.entity.plane.PlaneData;
import com.example.demo.util.Vector;

import java.util.List;

public class VFormationFactory extends FormationFactory {
    private static final Vector POSSIBLE_POSITION_1 = new Vector(512.0, 320.0);
    private static final Vector POSSIBLE_POSITION_2 = new Vector(416.0, 256.0);
    private static final Vector POSSIBLE_POSITION_3 = new Vector(608.0, 256.0);
    private static final Vector POSSIBLE_POSITION_4 = new Vector(320.0, 192.0);
    private static final Vector POSSIBLE_POSITION_5 = new Vector(704.0, 192.0);
    private static final Vector POSSIBLE_POSITION_6 = new Vector(800.0, 128.0);
    private static final Vector POSSIBLE_POSITION_7 = new Vector(224.0, 128.0);

    public VFormationFactory(Controller controller, PlaneData enemyPlaneData) {
        super(controller, enemyPlaneData, 0.0);
    }

    public VFormationFactory(Controller controller, PlaneData enemyPlaneData, double yOffset) {
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
