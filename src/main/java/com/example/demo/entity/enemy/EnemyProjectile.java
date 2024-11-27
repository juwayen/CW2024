package com.example.demo.entity.enemy;

import com.example.demo.entity.Projectile;

public class EnemyProjectile extends Projectile {
	private static final String IMAGE_NAME = "enemy_projectile.png";
	private static final int IMAGE_HEIGHT = 25;
	private static final int HORIZONTAL_VELOCITY = -10;

	public EnemyProjectile(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public void updateEntity() {
		updatePosition();
	}
}
