package com.example.demo.entity;

import com.example.demo.controller.GameController;
import com.example.demo.util.Vector;
import javafx.geometry.Bounds;

import static com.example.demo.Main.SCREEN_HEIGHT;
import static com.example.demo.Main.SCREEN_WIDTH;

public abstract class Projectile extends Entity {
	private final Vector direction;
	private final double speed;
	private final int damageOutput;

	public Projectile(GameController gameController, String imageName, double initialXPos, double initialYPos, Vector direction, double speed, int damageOutput) {
		super(gameController, imageName, initialXPos, initialYPos);

		this.direction = direction;
		this.speed = speed;
		this.damageOutput = damageOutput;
	}

	@Override
	public void update() {
		updatePosition();
		updateOutOfBoundsCheck();
	}

	private void updatePosition() {
		move(direction, speed);
	}

	private void updateOutOfBoundsCheck() {
		if (isOutOfBounds())
			removeFromScene();
	}

	@Override
	public void onCollision(Collidable collidable) {
		if (!(collidable instanceof FighterPlane))
			return;

		if (this.isFriendly() == collidable.isFriendly())
			return;

		((FighterPlane) collidable).takeDamage(damageOutput);

		removeFromScene();
	}

	private boolean isOutOfBounds() {
		Bounds bounds = localToScene(getBoundsInLocal());

        return bounds.getMaxX() < 0 ||
				bounds.getMaxY() < 0 ||
				bounds.getMinX() > (double) SCREEN_WIDTH ||
				bounds.getMinY() > (double) SCREEN_HEIGHT;
	}
}
