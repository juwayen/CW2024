package com.example.demo.entity.plane;

import com.example.demo.controller.GameController;
import com.example.demo.entity.bullet.Bullet;
import com.example.demo.entity.bullet.BulletConfig;
import com.example.demo.entity.bullet.HeavyBulletConfig;
import com.example.demo.util.Vector;

import static com.example.demo.controller.GameLoop.MILLISECOND_DELAY;

public class BossPlane extends Plane {
	private static final String IMAGE_NAME = "boss_plane.png";
	private static final Vector INITIAL_POSITION = new Vector(84.0, -123.0);
	private static final int HEALTH = 150;
	private static final double MIN_MILLISECONDS_PER_FIRE = 500.0;
	private static final double MAX_MILLISECONDS_PER_FIRE = 2000.0;
	private static final Vector BULLET_DIRECTION = Vector.DOWN;
	private static final Vector BULLET_OFFSET = new Vector(428.0, 492.0);

	private final GameController gameController;
	private final BulletConfig bulletConfig;

	private double millisecondsBeforeNextShot;

	public BossPlane(GameController gameController) {
		super(gameController, IMAGE_NAME, INITIAL_POSITION, HEALTH);

		this.gameController = gameController;
		this.bulletConfig = new HeavyBulletConfig(this, BULLET_DIRECTION, BULLET_OFFSET);

		this.millisecondsBeforeNextShot = getRandomTime();
	}

	private double getRandomTime() {
		return Math.random() * (MAX_MILLISECONDS_PER_FIRE - MIN_MILLISECONDS_PER_FIRE + 1) + MIN_MILLISECONDS_PER_FIRE;
	}

	@Override
	protected void updatePosition() {}

	@Override
	public boolean canFire() {
		millisecondsBeforeNextShot -= MILLISECOND_DELAY;

        return millisecondsBeforeNextShot <= 0;
    }

	@Override
	public void fire() {
		Vector shootingPosition = getPosition().add(BULLET_OFFSET);
		Vector directionToPlayer = shootingPosition.directionTo(gameController.getPlayer().getCenterPosition());
		bulletConfig.setDirection(directionToPlayer);
		Bullet bullet = new Bullet(gameController, bulletConfig);

		bullet.addToScene();

		millisecondsBeforeNextShot = getRandomTime();
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
