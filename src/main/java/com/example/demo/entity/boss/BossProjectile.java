package com.example.demo.entity.boss;

import com.example.demo.controller.GameController;
import com.example.demo.entity.Projectile;
import com.example.demo.util.Vector;

public class BossProjectile extends Projectile {
	private static final String IMAGE_NAME = "fireball.png";
	private static final int IMAGE_HEIGHT = 75;
	private static final Vector DIRECTION = new Vector(-1, 0);
	private static final double SPEED = 0.5;
	private static final int DAMAGE = 2;

	public BossProjectile(GameController gameController, double initialXPos, double initialYPos) {
		super(gameController, IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, DIRECTION, SPEED, DAMAGE);
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
