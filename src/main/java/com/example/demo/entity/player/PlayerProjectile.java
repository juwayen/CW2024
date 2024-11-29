package com.example.demo.entity.player;

import com.example.demo.controller.GameController;
import com.example.demo.entity.Collidable;
import com.example.demo.entity.Projectile;
import com.example.demo.entity.enemy.EnemyPlane;
import com.example.demo.signal.Signal;
import com.example.demo.util.Vector;

public class PlayerProjectile extends Projectile {
	private static final String IMAGE_NAME = "player_projectile.png";
	private static final int IMAGE_HEIGHT = 5;
	private static final Vector DIRECTION = new Vector(1, 0);
	private static final double SPEED = 0.6;
	private static final int DAMAGE = 1;

	private final Signal enemyPlaneDestroyed;

	public PlayerProjectile(GameController gameController, double initialXPos, double initialYPos) {
		super(gameController, IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DIRECTION, SPEED, DAMAGE);

		this.enemyPlaneDestroyed = new Signal();
	}

	public Signal getEnemyPlaneDestroyedSignal() {
		return enemyPlaneDestroyed;
	}

	@Override
	public void onCollision(Collidable collidable) {
		super.onCollision(collidable);

		if (collidable instanceof EnemyPlane) {
			if (((EnemyPlane) collidable).getHealth() <= 0)
				enemyPlaneDestroyed.emit();
		}
	}

	@Override
	public boolean isFriendly() {
		return true;
	}
}
