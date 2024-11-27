package com.example.demo.entity;

public class UserProjectile extends Projectile {
	private static final String IMAGE_NAME = "userfire.png";
	private static final int IMAGE_HEIGHT = 5;
	private static final int HORIZONTAL_VELOCITY = 20;

	public UserProjectile(double initialXPos, double initialYPos) {
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
