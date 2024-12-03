package com.example.demo.entity.enemy;

import com.example.demo.controller.GameController;
import com.example.demo.entity.FighterPlane;
import com.example.demo.signal.Signal;
import com.example.demo.util.Vector;

import static com.example.demo.Main.OUTPUT_SCALE;

public class EnemyPlane extends FighterPlane {
	private static final String IMAGE_NAME = "enemy_plane.png";
	private static final int INITIAL_HEALTH = 1;
	private static final int MIN_FRAMES_PER_FIRE = 120;
	private static final int MAX_FRAMES_PER_FIRE = 150;
	private static final Vector PROJECTILE_POSITION_OFFSET = new Vector(16, 64);
	private static final Vector DIRECTION = new Vector(0, 1);
	private static final double SPEED = 0.48;

	private final GameController gameController;
	private final Signal defensesBreached;

	private int framesBeforeNextShot;

	public EnemyPlane(GameController gameController, double initialXPos, double initialYPos) {
		super(gameController, IMAGE_NAME, initialXPos, initialYPos, INITIAL_HEALTH);

		this.gameController = gameController;
		this.defensesBreached = new Signal();
	}

	public Signal getDefensesBreachedSignal() {
		return defensesBreached;
	}

	@Override
	protected void clearSignalsConnections() {
		super.clearSignalsConnections();
		defensesBreached.clearConnections();
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
		return getTranslateY() > 1041 / OUTPUT_SCALE;
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
		double projectilePosX = PROJECTILE_POSITION_OFFSET.getX() + getTranslateX() * OUTPUT_SCALE;
		double projectilePosY = PROJECTILE_POSITION_OFFSET.getY() + getTranslateY() * OUTPUT_SCALE;
		EnemyProjectile projectile = new EnemyProjectile(gameController, projectilePosX, projectilePosY);

		projectile.addToScene();

		framesBeforeNextShot = (int)(Math.random() * (MAX_FRAMES_PER_FIRE - MIN_FRAMES_PER_FIRE + 1)) + MIN_FRAMES_PER_FIRE;
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
