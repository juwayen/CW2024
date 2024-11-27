package com.example.demo.entity;

public abstract class EntityDestructible extends Entity implements Destructible {
	private boolean isDestroyed;

	public EntityDestructible(String imageName, int imageHeight, double initialXPos, double initialYPos) {
		super(imageName, imageHeight, initialXPos, initialYPos);
		isDestroyed = false;
	}

	@Override
	public abstract void updatePosition();

	public abstract void updateEntity();

	@Override
	public abstract void takeDamage();

	@Override
	public void destroy() {
		isDestroyed = true;
	}

	public boolean isDestroyed() {
		return isDestroyed;
	}
}
