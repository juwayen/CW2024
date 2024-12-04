package com.example.demo.entity.bullet;

import com.example.demo.controller.GameController;
import com.example.demo.controller.Collidable;
import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.Plane;
import com.example.demo.util.Vector;
import javafx.geometry.Bounds;

import static com.example.demo.Main.*;

public class Bullet extends Entity {
	private final boolean isFriendly;
	private final Vector direction;
	private final double speed;
	private final int damage;

	public Bullet(GameController gameController, BulletConfig bulletConfig) {
		super(gameController, bulletConfig.getImageName(), bulletConfig.getInitialPosition());

		this.isFriendly = bulletConfig.getIsFriendly();
		this.direction = bulletConfig.getDirection();
		this.speed = bulletConfig.getSpeed();
		this.damage = bulletConfig.getDamage();
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

	private boolean isOutOfBounds() {
		Bounds bounds = getBoundsInParent();

		return  bounds.getMaxX() < 0 ||
				bounds.getMaxY() < 0 ||
				bounds.getMinX() > GAME_WIDTH / OUTPUT_SCALE ||
				bounds.getMinY() > GAME_HEIGHT / OUTPUT_SCALE;
	}

	@Override
	public void onCollision(Collidable collidable) {
		if (!(collidable instanceof Plane plane))
			return;

        if (isFriendly == plane.isFriendly())
			return;

		plane.takeDamage(damage);

		removeFromScene();
	}
}
