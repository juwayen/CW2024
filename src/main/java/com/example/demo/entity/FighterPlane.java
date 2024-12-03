package com.example.demo.entity;

import com.example.demo.controller.GameController;
import com.example.demo.signal.Signal;

public abstract class FighterPlane extends Entity {
	private final Signal destroyed;

	private int health;

	public FighterPlane(GameController gameController, String imageName, double initialXPos, double initialYPos, int health) {
		super(gameController, imageName, initialXPos, initialYPos);

		this.destroyed = new Signal();
		this.health = health;
	}

	public Signal getDestroyedSignal() {
		return destroyed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	@Override
	protected void clearSignalsConnections() {
		super.clearSignalsConnections();
		destroyed.clearConnections();
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
			destroyed.emit();
			removeFromScene();
		}
	}
}
