package com.example.demo.factory;

import com.example.demo.util.Vector;

import java.util.List;

public class RandomFormFormFactory extends FormFactory {
    public RandomFormFormFactory(EnemyPlaneFactory enemyPlaneFactory) {
        super(enemyPlaneFactory);
    }

    @Override
    protected void initializePossiblePositions(List<Vector> possiblePositions) {}
}
