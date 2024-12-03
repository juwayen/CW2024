package com.example.demo.entity.player;

import com.example.demo.controller.GameController;
import com.example.demo.entity.Projectile;
import com.example.demo.util.Vector;

public class PlayerProjectile extends Projectile {
	private static final String IMAGE_NAME = "player_projectile.png";
	private static final Vector DIRECTION = new Vector(0, -1);
	private static final double SPEED = 3.84;
	private static final int DAMAGE = 1;

	public PlayerProjectile(GameController gameController, double initialXPos, double initialYPos) {
		super(gameController, IMAGE_NAME, initialXPos, initialYPos, DIRECTION, SPEED, DAMAGE);
	}

	@Override
	public boolean isFriendly() {
		return true;
	}
}
