package com.example.demo.factory;

import com.example.demo.entity.plane.PlaneData;
import com.example.demo.util.Vector;

import java.util.List;

public class RandomFormationFactory extends FormationFactory {
    public RandomFormationFactory(PlaneData enemyPlaneData) {
        super(enemyPlaneData, 0.0);
    }

    @Override
    protected void initializePossiblePositions(List<Vector> possiblePositions) {}
}
