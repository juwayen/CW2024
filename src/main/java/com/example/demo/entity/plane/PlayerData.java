package com.example.demo.entity.plane;

import com.example.demo.util.ImageUtils;

public class PlayerData extends PlaneData {
    private static final String MOVING_STRAIGHT_1_IMAGE_NAME = "player_plane_moving_straight_1.png";
    private static final String MOVING_STRAIGHT_2_IMAGE_NAME = "player_plane_moving_straight_2.png";
    private static final String MOVING_RIGHT_1_IMAGE_NAME = "player_plane_moving_right_1.png";
    private static final String MOVING_RIGHT_2_IMAGE_NAME = "player_plane_moving_right_2.png";
    private static final String MOVING_LEFT_1_IMAGE_NAME = "player_plane_moving_left_1.png";
    private static final String MOVING_LEFT_2_IMAGE_NAME = "player_plane_moving_left_2.png";
    private static final String DESTROYED_1_IMAGE_NAME = "plane_destroyed_1.png";
    private static final String DESTROYED_2_IMAGE_NAME = "plane_destroyed_2.png";
    private static final String DESTROYED_3_IMAGE_NAME = "plane_destroyed_3.png";
    private static final String DESTROYED_4_IMAGE_NAME = "plane_destroyed_4.png";
    private static final String DESTROYED_5_IMAGE_NAME = "plane_destroyed_5.png";
    private static final String DESTROYED_6_IMAGE_NAME = "plane_destroyed_6.png";
    private static final String DESTROYED_7_IMAGE_NAME = "plane_destroyed_7.png";
    private static final String DESTROYED_8_IMAGE_NAME = "plane_destroyed_8.png";

    public PlayerData() {
        super();

        this.addMovingStraightImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_1_IMAGE_NAME));
        this.addMovingStraightImage(ImageUtils.getImageFromName(MOVING_STRAIGHT_2_IMAGE_NAME));

        this.addMovingRightImage(ImageUtils.getImageFromName(MOVING_RIGHT_1_IMAGE_NAME));
        this.addMovingRightImage(ImageUtils.getImageFromName(MOVING_RIGHT_2_IMAGE_NAME));

        this.addMovingLeftImage(ImageUtils.getImageFromName(MOVING_LEFT_1_IMAGE_NAME));
        this.addMovingLeftImage(ImageUtils.getImageFromName(MOVING_LEFT_2_IMAGE_NAME));

        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_1_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_2_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_3_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_4_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_5_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_6_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_7_IMAGE_NAME));
        this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_8_IMAGE_NAME));


    }
}
