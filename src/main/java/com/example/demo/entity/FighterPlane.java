package com.example.demo.entity;

import com.example.demo.controller.GameController;
import com.example.demo.signal.Signal;

public abstract class FighterPlane extends Entity {
	private final Signal planeDestroyed;

	private int health;

	public FighterPlane(GameController gameController, String imageName, int imageHeight, double initialXPos, double initialYPos, int health) {
		super(gameController, imageName, imageHeight, initialXPos, initialYPos);

		this.planeDestroyed = new Signal();
		this.health = health;
	}

	public Signal getPlaneDestroyedSignal() {
		return planeDestroyed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	public void update() {
		updatePosition();
		updateFire();
	}

	protected abstract void updatePosition();

	protected void updateFire() {
		if (canFireProjectile())
			fireProjectile();
	}

	protected abstract boolean canFireProjectile();

	protected abstract void fireProjectile();

	protected void takeDamage(int damageAmount) {
		health -= damageAmount;

		if (health <= 0) {
			removeFromScene();
			planeDestroyed.emit();
		}
	}
}
