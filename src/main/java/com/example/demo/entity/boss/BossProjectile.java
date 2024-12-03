package com.example.demo.entity.boss;

import com.example.demo.controller.GameController;
import com.example.demo.entity.Projectile;
import com.example.demo.util.Vector;

public class BossProjectile extends Projectile {
	private static final String IMAGE_NAME = "boss_projectile.png";
	private static final Vector DIRECTION = new Vector(0, 1);
	private static final double SPEED = 0.96;
	private static final int DAMAGE = 2;

	public BossProjectile(GameController gameController, double initialXPos, double initialYPos) {
		super(gameController, IMAGE_NAME, initialXPos, initialYPos, DIRECTION, SPEED, DAMAGE);
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
