package com.example.demo.entity.boss;

import com.example.demo.controller.GameController;
import com.example.demo.entity.FighterPlane;
import com.example.demo.util.Vector;

import static com.example.demo.Main.OUTPUT_SCALE;

public class BossPlane extends FighterPlane {
	private static final String IMAGE_NAME = "boss_plane.png";
	private static final double INITIAL_X_POSITION = 84;
	private static final double INITIAL_Y_POSITION = -123;
	private static final int HEALTH = 150;
	private static final int MIN_FRAMES_PER_FIRE = 60;
	private static final int MAX_FRAMES_PER_FIRE = 150;
	private static final Vector PROJECTILE_POSITION_OFFSET = new Vector(412, 492);

	private final GameController gameController;

	private int framesBeforeNextShot = 0;

	public BossPlane(GameController gameController) {
		super(gameController, IMAGE_NAME, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);

		this.gameController = gameController;
	}

	@Override
	protected void updatePosition() {}

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
		BossProjectile projectile = new BossProjectile(gameController, projectilePosX, projectilePosY);

		projectile.addToScene();

		framesBeforeNextShot = (int)(Math.random() * (MAX_FRAMES_PER_FIRE - MIN_FRAMES_PER_FIRE + 1)) + MIN_FRAMES_PER_FIRE;
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
