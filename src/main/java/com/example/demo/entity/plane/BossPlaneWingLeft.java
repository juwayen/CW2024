package com.example.demo.entity.plane;

import com.example.demo.util.Vector;

public class BossPlaneWingLeft extends EnemyPlane {
    public BossPlaneWingLeft(double initialX, Vector finalPosition) {
        super(new BossWingLeftData(initialX, finalPosition));

        initialize();
    }

    private void initialize() {
        getDestroyedTimeline().setOnFinished(event -> {
            getDestroyedSignal().emit();
            emitRemovedSignal();
        });
    }
}
