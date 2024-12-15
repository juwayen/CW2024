package com.example.demo.entity.plane;

import com.example.demo.util.Vector;

/**
 * Represents the right wing of {@link BossData}.
 */
public class BossPlaneWingRight extends EnemyPlane {
    /**
     * Creates an instance of {@link BossPlaneWingRight}, with specific initial and final positions.
     *
     * @param initialX The initial x-coordinate of the wing.
     * @param finalPosition The target {@link Vector} indicating the final position of the wing.
     */
    public BossPlaneWingRight(double initialX, Vector finalPosition) {
        super(new BossWingRightData(initialX, finalPosition));

        initialize();
    }

    /**
     * Replaces event handler from {@link Plane} with one that doesn't disable the visuals after the object is destroyed.
     */
    private void initialize() {
        getDestroyedTimeline().setOnFinished(event -> {
            getDestroyedSignal().emit();
            emitRemovedSignal();
        });
    }
}
