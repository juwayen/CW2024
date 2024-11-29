package com.example.demo.entity.enemy;

import com.example.demo.controller.GameController;
import com.example.demo.entity.FighterPlane;
import com.example.demo.signal.Signal;
import com.example.demo.util.Vector;

public class EnemyPlane extends FighterPlane {
	private static final String IMAGE_NAME = "enemy_plane.png";
	private static final int IMAGE_HEIGHT = 50;
	private static final int INITIAL_HEALTH = 1;
	private static final int MIN_FRAMES_PER_FIRE = 60;
	private static final int MAX_FRAMES_PER_FIRE = 150;
	private static final Vector PROJECTILE_POSITION_OFFSET = new Vector(-75, 25);
	private static final Vector DIRECTION = new Vector(-1, 0);
	private static final double SPEED = 0.25;

	private final GameController gameController;
	private final Signal defensesBreached;

	private int framesBeforeNextShot;

	public EnemyPlane(GameController gameController, double initialXPos, double initialYPos) {
		super(gameController, IMAGE_NAME, IMAGE_HEIGHT, initialXPos, initialYPos, INITIAL_HEALTH);

		this.gameController = gameController;
		this.defensesBreached = new Signal();
	}

	public Signal getDefensesBreachedSignal() {
		return defensesBreached;
	}

	@Override
	public void update() {
		super.update();
		updateBreachedDefenses();
	}

	private void updateBreachedDefenses() {
		if (hasBreachedDefenses())
			defensesBreached.emit();
	}

	private boolean hasBreachedDefenses() {
		return getTranslateX() + getLayoutX() < 0;
	}

	@Override
	public void updatePosition() {
		move(DIRECTION, SPEED);
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
		EnemyProjectile projectile = new EnemyProjectile(gameController, projectilePosX, projectilePosY);

		projectile.addToScene();

		framesBeforeNextShot = (int)(Math.random() * (MAX_FRAMES_PER_FIRE - MIN_FRAMES_PER_FIRE + 1)) + MIN_FRAMES_PER_FIRE;
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
