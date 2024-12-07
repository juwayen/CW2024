package com.example.demo.entity.plane;

import com.example.demo.Controller;
import com.example.demo.entity.bullet.*;
import com.example.demo.util.Vector;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

public class EnemyPlane extends Plane {
	public static final int INITIAL_HEALTH = 2;
	private static final double MIN_MILLISECONDS_PER_FIRE = 500.0;
	private static final double MAX_MILLISECONDS_PER_FIRE = 1000.0;
	private static final Vector DIRECTION = Vector.DOWN;
	private static final double SPEED = 0.48;
	private static final double MAX_Y = 128.0;
	private static final Vector BULLET_OFFSET = new Vector(48.0, 64.0);

	private final Controller controller;
	private final BulletConfig bulletConfig;

	private double millisecondsBeforeNextShot;

	public EnemyPlane(Controller controller, Vector initialPosition) {
		super(controller, new EnemyPlaneImageData(), initialPosition, INITIAL_HEALTH);

		this.controller = controller;
		this.bulletConfig = new BasicBulletConfig(this, null, BULLET_OFFSET);

		this.millisecondsBeforeNextShot = getRandomTime();
	}

	private double getRandomTime() {
		return Math.random() * (MAX_MILLISECONDS_PER_FIRE - MIN_MILLISECONDS_PER_FIRE + 1) + MIN_MILLISECONDS_PER_FIRE;
	}

	@Override
	public void updatePosition() {
		if (getPosition().getY() > MAX_Y)
			return;

		move(DIRECTION, SPEED);
	}

	@Override
	public boolean canFire() {
		millisecondsBeforeNextShot -= MILLISECOND_DELAY;

		return millisecondsBeforeNextShot <= 0;
	}

	@Override
	public void fire() {
		Vector shootingPosition = getPosition().add(bulletConfig.getOffset());
		Vector directionToPlayer = shootingPosition.directionTo(controller.getPlayer().getCenterPosition());
		bulletConfig.setDirection(directionToPlayer);
		Bullet bullet = new Bullet(controller, bulletConfig);

		bullet.addToScene();

		millisecondsBeforeNextShot = getRandomTime();
	}

	@Override
	public boolean isFriendly() {
		return false;
	}
}
