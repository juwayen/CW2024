package com.example.demo.entity.bullet;

import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;

/**
 * Represents the configuration for a basic bullet, extending the {@link BulletConfig} class.
 */
public class DoubleBulletConfig extends BulletConfig {
	private static final String IMAGE_NAME = "double_bullet.png";
	private static final String DESTROYED_1_IMAGE_NAME = "double_bullet_destroyed_1.png";
	private static final String DESTROYED_2_IMAGE_NAME = "double_bullet_destroyed_2.png";
	private static final String DESTROYED_3_IMAGE_NAME = "double_bullet_destroyed_3.png";
	private static final double SPEED = 1.92;
	private static final int DAMAGE = 2;

	/**
	 * Constructs a new {@link BasicBulletConfig} object with the specified offset.
	 *
	 * @param offset The {@link Vector} representing the initial position offset.
	 */
	public DoubleBulletConfig(Vector offset) {
		super();

		this.setOffset(offset);
		this.setImage(ImageUtils.getImageFromName(IMAGE_NAME));

		this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_1_IMAGE_NAME));
		this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_2_IMAGE_NAME));
		this.addDestroyedImage(ImageUtils.getImageFromName(DESTROYED_3_IMAGE_NAME));

		this.setSpeed(SPEED);
		this.setDamage(DAMAGE);
	}
}