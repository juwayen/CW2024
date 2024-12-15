package com.example.demo.entity.plane;

import com.example.demo.util.Vector;

/**
 * Represents a specialized {@link EnemyPlane}.
 */
public class BossPlane extends EnemyPlane {
    private static final Vector WING_LEFT_OFFSET = new Vector(-396.0, 120.0);
    private static final Vector WING_RIGHT_OFFSET = new Vector(64.0, 120.0);
    private static final int DAMAGE_FROM_WING_DESTROYED = 100;

    private final BossPlaneWingLeft wingLeft;
    private final BossPlaneWingRight wingRight;

    /**
     * Constructs an instance of {@link BossPlane} based on the given {@link PlaneData}.
     * Instantiates {@link BossPlaneWingLeft} and {@link BossPlaneWingRight}.
     *
     * @param bossData The {@link BossData} instance containing the initialization data.
     */
    public BossPlane(BossData bossData) {
        super(bossData);

        this.wingLeft = new BossPlaneWingLeft(bossData.getInitialPosition().add(WING_LEFT_OFFSET).getX(), bossData.getFinalPosition().add(WING_LEFT_OFFSET));
        this.wingRight = new BossPlaneWingRight(bossData.getInitialPosition().add(WING_RIGHT_OFFSET).getX(), bossData.getFinalPosition().add(WING_RIGHT_OFFSET));

        initializeWings();
    }

    /**
     * Initializes the wings of the {@link BossPlane} by connecting each wing's destroyed {@link com.example.demo.util.Signal} to {@link #takeDamage(int)}.
     * When a wing is destroyed, the {@link BossPlane} takes a predefined amount of damage.
     */
    private void initializeWings() {
        wingLeft.getDestroyedSignal().connect(() -> takeDamage(DAMAGE_FROM_WING_DESTROYED));
        wingRight.getDestroyedSignal().connect(() -> takeDamage(DAMAGE_FROM_WING_DESTROYED));
    }

    /**
     * Applies damage to the {@link BossPlane} by reducing its health.
     *
     * @param damageAmount The amount of damage to be applied.
     */
    @Override
    public void takeDamage(int damageAmount) {
        super.takeDamage(damageAmount);

        if (getHealth() <= 0) {
            wingLeft.removeFromScene();
            wingRight.removeFromScene();
        }
    }

    /**
     * Adds the {@link BossPlane} and its wings ({@link BossPlaneWingLeft} and {@link BossPlaneWingRight}) to the scene.
     */
    @Override
    public void addToScene() {
        super.addToScene();

        wingLeft.addToScene();
        wingRight.addToScene();
    }
}
