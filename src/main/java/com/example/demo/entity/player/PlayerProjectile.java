package com.example.demo.entity.player;

import com.example.demo.entity.Projectile;

public class PlayerProjectile extends Projectile {
	private static final String IMAGE_NAME = "player_projectile.png";
	private static final int IMAGE_HEIGHT = 5;
	private static final int HORIZONTAL_VELOCITY = 20;

	public PlayerProjectile(double initialXPos, double initialYPos) {
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
