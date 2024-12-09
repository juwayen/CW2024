package com.example.demo.factory;

import com.example.demo.Controller;
import com.example.demo.entity.plane.AdvancedEnemy;
import com.example.demo.entity.plane.EnemyPlane;

public class AdvancedEnemyFactory extends EnemyPlaneFactory {
    public AdvancedEnemyFactory(Controller controller) {
        super(controller);
    }

    @Override
    public EnemyPlane create() {
        return new AdvancedEnemy(getController(), getInitialX(), getFinalPosition());
    }
}
