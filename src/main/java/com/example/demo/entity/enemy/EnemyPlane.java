package com.example.demo.entity.enemy;

import com.example.demo.entity.EntityDestructible;
import com.example.demo.entity.FighterPlane;

public class EnemyPlane extends FighterPlane {
	private static final String IMAGE_NAME = "enemy_plane.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int HORIZONTAL_VELOCITY = -6;
	private static final double PROJECTILE_X_POSITION_OFFSET = -75.0;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 25.0;
	private static final int INITIAL_HEALTH = 1;
	private static final int MIN_FRAMES_PER_FIRE = 60;
	private static final int MAX_FRAMES_PER_FIRE = 150;

	private int framesBeforeNextShot;

	public EnemyPlane(double initialXPos, double initialYPos) {
		super(IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);
	}

	@Override
	public void updatePosition() {
		moveHorizontally(HORIZONTAL_VELOCITY);
	}

	@Override
	public EntityDestructible fireProjectile() {
		if (framesBeforeNextShot <= 0) {
			framesBeforeNextShot = (int)(Math.random() * (MAX_FRAMES_PER_FIRE - MIN_FRAMES_PER_FIRE + 1)) + MIN_FRAMES_PER_FIRE;
			double projectileXPosition = getProjectileXPosition(PROJECTILE_X_POSITION_OFFSET);
			double projectileYPosition = getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET);
			return new EnemyProjectile(projectileXPosition, projectileYPosition);
		}

		framesBeforeNextShot--;

		return null;
	}

	@Override
	public void updateEntity() {
		updatePosition();
	}
}
