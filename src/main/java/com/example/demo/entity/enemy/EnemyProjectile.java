package com.example.demo.entity.enemy;

import com.example.demo.controller.GameController;
import com.example.demo.entity.Projectile;
import com.example.demo.util.Vector;

public class EnemyProjectile extends Projectile {
	private static final String IMAGE_NAME = "enemy_projectile.png";
	private static final int IMAGE_HEIGHT = 25;
	private static final Vector DIRECTION = new Vector(-1, 0);
	private static final double SPEED = 0.4;
	private static final int DAMAGE = 1;

	public EnemyProjectile(GameController gameController, double initialXPos, double initialYPos) {
		super(gameController, IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DIRECTION, SPEED, DAMAGE);
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
