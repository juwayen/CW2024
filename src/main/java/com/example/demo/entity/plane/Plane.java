package com.example.demo.entity.plane;

import com.example.demo.GameController;
import com.example.demo.entity.Entity;
import com.example.demo.util.Signal;
import com.example.demo.util.Vector;

public abstract class Plane extends Entity {
	private final Signal destroyed;

	private int health;

	public Plane(GameController gameController, String imageName, Vector initialPos, int health) {
		super(gameController, imageName, initialPos);

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
		if (canFire())
			fire();
	}

	protected abstract boolean canFire();

	protected abstract void fire();

	public void takeDamage(int damageAmount) {
		health -= damageAmount;

		if (health <= 0) {
			destroyed.emit();
			removeFromScene();
		}
	}

	public abstract boolean isFriendly();
}
