package com.example.demo.entity.plane;

import com.example.demo.util.Vector;

public class BossPlane extends EnemyPlane {
    private static final Vector WING_LEFT_OFFSET = new Vector(-396.0, 120.0);
    private static final Vector WING_RIGHT_OFFSET = new Vector(64.0, 120.0);
    private static final int DAMAGE_FROM_WING_DESTROYED = 100;

    private final BossPlaneWingLeft wingLeft;
    private final BossPlaneWingRight wingRight;

    public BossPlane(PlaneData planeData) {
        super(planeData);

        this.wingLeft = new BossPlaneWingLeft(planeData.getInitialPosition().add(WING_LEFT_OFFSET).getX(), planeData.getFinalPosition().add(WING_LEFT_OFFSET));
        this.wingRight = new BossPlaneWingRight(planeData.getInitialPosition().add(WING_RIGHT_OFFSET).getX(), planeData.getFinalPosition().add(WING_RIGHT_OFFSET));

        initialize();
    }

    private void initialize() {
        initializeWings();
    }

    private void initializeWings() {
        wingLeft.getDestroyedSignal().connect(() -> takeDamage(DAMAGE_FROM_WING_DESTROYED));
        wingRight.getDestroyedSignal().connect(() -> takeDamage(DAMAGE_FROM_WING_DESTROYED));
    }

    @Override
    public void takeDamage(int damageAmount) {
        super.takeDamage(damageAmount);

        if (getHealth() <= 0) {
            wingLeft.removeFromScene();
            wingRight.removeFromScene();
        }
    }

    @Override
    public void addToScene() {
        super.addToScene();

        wingLeft.addToScene();
        wingRight.addToScene();
    }
}
