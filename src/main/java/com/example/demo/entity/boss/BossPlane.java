package com.example.demo.entity.boss;

import com.example.demo.controller.GameController;
import com.example.demo.entity.FighterPlane;
import com.example.demo.util.Vector;

import java.util.*;

public class BossPlane extends FighterPlane {
	private static final String IMAGE_NAME = "boss_plane.png";
	private static final double INITIAL_X_POSITION = 1040;
	private static final double INITIAL_Y_POSITION = 300;
	private static final double BOSS_SHIELD_PROBABILITY = 0.25;
	private static final int IMAGE_HEIGHT = 80;
	private static final int SPEED = 1;
	private static final int HEALTH = 50;
	private static final int MIN_FRAMES_PER_FIRE = 120;
	private static final int MAX_FRAMES_PER_FIRE = 300;
	private static final int Y_POSITION_UPPER_BOUND = 0;
	private static final int Y_POSITION_LOWER_BOUND = 600;
	private static final int FRAMES_PER_SHIELD_CHANCE = 300;
	private static final int MAX_FRAMES_WITH_SHIELD = 90;
	private static final Vector DIRECTION = new Vector(0, 1);
	private static final Vector PROJECTILE_POSITION_OFFSET = new Vector(0, 0);

	private final GameController gameController;

	private boolean isShielded = false;
	private int framesBeforeNextShot = 0;
	private int framesBeforeNextShieldChance = 0;
	private int framesWithShieldActivated = 0;

	public BossPlane(GameController gameController) {
		super(gameController, IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);

		this.gameController = gameController;
	}

	@Override
	public void update() {
		super.update();
		updateShield();
	}

	public void updatePosition() {
		move(DIRECTION, getNextMove());
	}

	private int getNextMove() {
		return 0;
	}

	private void updateShield() {
		if (isShielded)
			framesWithShieldActivated++;
		else if (shieldShouldBeActivated())
			activateShield();

		if (shieldExhausted())
			deactivateShield();
	}

	private boolean shieldShouldBeActivated() {
		if (framesBeforeNextShieldChance <= 0) {
			framesBeforeNextShieldChance = FRAMES_PER_SHIELD_CHANCE;
			return Math.random() < BOSS_SHIELD_PROBABILITY;
		}

		framesBeforeNextShieldChance--;

		return false;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD;
	}

	@Override
	public boolean canFireProjectile() {
		if (framesBeforeNextShot <= 0)
			return true;

		framesBeforeNextShot--;

		return false;
	}

	@Override
	public void fireProjectile() {
		double projectilePosX = PROJECTILE_POSITION_OFFSET.getX() + getTranslateX() + getLayoutX();
		double projectilePosY = PROJECTILE_POSITION_OFFSET.getY() + getTranslateY() + getLayoutY();
		BossProjectile projectile = new BossProjectile(gameController, projectilePosX, projectilePosY);

		projectile.addToScene();

		framesBeforeNextShot = (int)(Math.random() * (MAX_FRAMES_PER_FIRE - MIN_FRAMES_PER_FIRE + 1)) + MIN_FRAMES_PER_FIRE;
	}
	
	@Override
	public void takeDamage(int damageAmount) {
		if (!isShielded)
			super.takeDamage(damageAmount);
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
