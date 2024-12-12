package com.example.demo.entity.plane;

import com.example.demo.entity.bullet.HeavyBulletConfig;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;

/**
 * Represents the data for a boss enemy, extending the {@link PlaneData} class.
 */
public class BossData extends PlaneData {
    private static final String IMAGE_NAME = "boss_plane_body.png";
    private static final String DESTROYED_1_IMAGE_NAME = "boss_destroyed_1.png";
    private static final String DESTROYED_2_IMAGE_NAME = "boss_destroyed_2.png";
    private static final String DESTROYED_3_IMAGE_NAME = "boss_destroyed_3.png";
    private static final String DESTROYED_4_IMAGE_NAME = "boss_destroyed_4.png";
    private static final String DESTROYED_5_IMAGE_NAME = "boss_destroyed_5.png";
    private static final String DESTROYED_6_IMAGE_NAME = "boss_destroyed_6.png";
    private static final String DESTROYED_7_IMAGE_NAME = "boss_destroyed_7.png";
    private static final String DESTROYED_8_IMAGE_NAME = "boss_destroyed_8.png";
    private static final double INITIAL_POSITION_Y = -400.0;
    private static final int HEALTH = 250;
    private static final double MIN_MILLISECONDS_PER_FIRE = 2500.0;
    private static final double MAX_MILLISECONDS_PER_FIRE = 5000.0;
    private static final double SPEED = 0.36;
    private static final Vector BULLET_OFFSET = new Vector(32.0, 400.0);

    /**
     * Constructs a new {@link BossData} object with predefined data.
     */
    public BossData() {
        super();

        this.addMovingStraightImage(ImageUtils.getImageFromName(IMAGE_NAME));

        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_1_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_2_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_3_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_4_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_5_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_6_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_7_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_8_IMAGE_NAME));

        this.setInitialPosition(new Vector(0.0, INITIAL_POSITION_Y));
        this.setFinalPosition(null);
        this.setHealth(HEALTH);
        this.setMinMillisecondsPerFire(MIN_MILLISECONDS_PER_FIRE);
        this.setMaxMillisecondsPerFire(MAX_MILLISECONDS_PER_FIRE);
        this.setSpeed(SPEED);
        this.setBulletConfig(new HeavyBulletConfig(BULLET_OFFSET));
    }
}
