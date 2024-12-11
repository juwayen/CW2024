package com.example.demo.entity.plane;

import com.example.demo.util.Vector;

public class BossPlaneWingRight extends EnemyPlane {
    public BossPlaneWingRight(double initialX, Vector finalPosition) {
        super(new BossWingRightData(initialX, finalPosition));

        initialize();
    }

    private void initialize() {
        getDestroyedTimeline().setOnFinished(event -> {
            getDestroyedSignal().emit();
            emitRemovedSignal();
        });
    }
}
