package com.example.demo.factory;

import com.example.demo.Controller;
import com.example.demo.entity.plane.BasicEnemy;
import com.example.demo.entity.plane.EnemyPlane;

public class BasicEnemyFactory extends EnemyPlaneFactory {
    public BasicEnemyFactory(Controller controller) {
        super(controller);
    }

    @Override
    public EnemyPlane create() {
        return new BasicEnemy(getController(), getInitialX(), getFinalPosition());
    }
}
