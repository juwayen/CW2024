package com.example.demo.entity.plane;

import com.example.demo.entity.bullet.BasicBulletConfig;
import com.example.demo.entity.bullet.BulletConfig;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;

/**
 * Represents the data for a basic enemy, extending the {@link PlaneData} class.
 */
public class BasicEnemyData extends PlaneData {
    private static final String MOVING_STRAIGHT_1_IMAGE_NAME = "basic_enemy_plane_moving_straight_1.png";
    private static final String MOVING_STRAIGHT_2_IMAGE_NAME = "basic_enemy_plane_moving_straight_2.png";
    private static final String DESTROYED_1_IMAGE_NAME = "plane_destroyed_1.png";
    private static final String DESTROYED_2_IMAGE_NAME = "plane_destroyed_2.png";
    private static final String DESTROYED_3_IMAGE_NAME = "plane_destroyed_3.png";
    private static final String DESTROYED_4_IMAGE_NAME = "plane_destroyed_4.png";
    private static final String DESTROYED_5_IMAGE_NAME = "plane_destroyed_5.png";
    private static final String DESTROYED_6_IMAGE_NAME = "plane_destroyed_6.png";
    private static final String DESTROYED_7_IMAGE_NAME = "plane_destroyed_7.png";
    private static final String DESTROYED_8_IMAGE_NAME = "plane_destroyed_8.png";
    private static final double INITIAL_POSITION_Y = -64.0;
    private static final int HEALTH = 2;
    private static final double MIN_MILLISECONDS_PER_FIRE = 1000.0;
    private static final double MAX_MILLISECONDS_PER_FIRE = 3000.0;
    private static final double SPEED = 0.48;
    private static final Vector BULLET_OFFSET = new Vector(48.0, 64.0);

    /**
     * Constructs a new {@link BasicEnemyData} object with predefined data.
     */
    public BasicEnemyData() {
        super();

        this.addMovingStraightImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_1_IMAGE_NAME));
        this.addMovingStraightImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_2_IMAGE_NAME));

        this.addMovingRightImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_1_IMAGE_NAME));
        this.addMovingRightImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_2_IMAGE_NAME));

        this.addMovingLeftImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_1_IMAGE_NAME));
        this.addMovingLeftImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_2_IMAGE_NAME));

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
        this.setBulletConfig(new BasicBulletConfig(BULLET_OFFSET));
    }
}
