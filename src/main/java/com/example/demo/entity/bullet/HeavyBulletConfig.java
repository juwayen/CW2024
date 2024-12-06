package com.example.demo.entity.bullet;

import com.example.demo.entity.plane.Plane;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;

public class HeavyBulletConfig extends BulletConfig {
	private static final String IMAGE_NAME = "heavy_bullet.png";
	private static final String DESTROYED_1_IMAGE_NAME = "bullet_destroyed_1.png";
	private static final String DESTROYED_2_IMAGE_NAME = "bullet_destroyed_2.png";
	private static final String DESTROYED_3_IMAGE_NAME = "bullet_destroyed_3.png";
	private static final double SPEED = 0.96;
	private static final int DAMAGE = 2;

	public HeavyBulletConfig(Plane shooter, Vector direction, Vector offset) {
		super();

		this.setShooter(shooter);
		this.setDirection(direction);
		this.setOffset(offset);
		this.setImageName(ImageUtils.getImageFromName(IMAGE_NAME));

		this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_1_IMAGE_NAME));
		this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_2_IMAGE_NAME));
		this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_3_IMAGE_NAME));

		this.setSpeed(SPEED);
		this.setDamage(DAMAGE);
	}
}
