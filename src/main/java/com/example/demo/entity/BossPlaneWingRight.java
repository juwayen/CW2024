package com.example.demo.entity;

import com.example.demo.Controller;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.util.Vector;

public class BossPlaneWingRight extends EnemyPlane {
    public BossPlaneWingRight(Controller controller, double initialX, Vector finalPosition) {
        super(controller, new BossWingRightData(initialX, finalPosition));

        initialize();
    }

    private void initialize() {
        getDestroyedTimeline().setOnFinished(event -> {
            getDestroyedSignal().emit();
            emitRemovedSignal();
        });
    }
}
