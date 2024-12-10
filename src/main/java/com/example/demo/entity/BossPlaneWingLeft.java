package com.example.demo.entity;

import com.example.demo.Controller;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.util.Vector;

public class BossPlaneWingLeft extends EnemyPlane {
    public BossPlaneWingLeft(Controller controller, double initialX, Vector finalPosition) {
        super(controller, new BossWingLeftData(initialX, finalPosition));

        initialize();
    }

    private void initialize() {
        getDestroyedTimeline().setOnFinished(event -> {
            getDestroyedSignal().emit();
            emitRemovedSignal();
        });
    }
}
