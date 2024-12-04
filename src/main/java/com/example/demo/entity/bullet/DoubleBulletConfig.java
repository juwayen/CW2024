package com.example.demo.entity.bullet;

import com.example.demo.entity.plane.Plane;
import com.example.demo.util.Vector;

public class DoubleBulletConfig extends BulletConfig {
	private static final String IMAGE_NAME = "double_bullet.png";
	private static final double SPEED = 3.84;
	private static final int DAMAGE = 2;

	public DoubleBulletConfig(Plane shooter, Vector direction, Vector offset) {
		this.setShooter(shooter);
		this.setDirection(direction);
		this.setOffset(offset);
		this.setImageName(IMAGE_NAME);
		this.setSpeed(SPEED);
		this.setDamage(DAMAGE);
	}
}