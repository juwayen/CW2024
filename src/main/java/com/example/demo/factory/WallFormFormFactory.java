package com.example.demo.factory;

import com.example.demo.util.Vector;

import java.util.List;

public class WallFormFormFactory extends FormFactory {
    private static final double POSSIBLE_POSITION_Y = 128.0;
    private static final Vector POSSIBLE_POSITION_1 = new Vector(464.0, POSSIBLE_POSITION_Y);
    private static final Vector POSSIBLE_POSITION_2 = new Vector(368.0, POSSIBLE_POSITION_Y);
    private static final Vector POSSIBLE_POSITION_3 = new Vector(560.0, POSSIBLE_POSITION_Y);
    private static final Vector POSSIBLE_POSITION_4 = new Vector(272.0, POSSIBLE_POSITION_Y);
    private static final Vector POSSIBLE_POSITION_5 = new Vector(656.0, POSSIBLE_POSITION_Y);
    private static final Vector POSSIBLE_POSITION_6 = new Vector(176.0, POSSIBLE_POSITION_Y);
    private static final Vector POSSIBLE_POSITION_7 = new Vector(752.0, POSSIBLE_POSITION_Y);


    public WallFormFormFactory(EnemyPlaneFactory enemyPlaneFactory) {
        super(enemyPlaneFactory);
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
