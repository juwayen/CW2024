package com.example.demo.entity.plane;

import com.example.demo.Controller;
import com.example.demo.entity.bullet.Bullet;
import com.example.demo.entity.bullet.BulletConfig;
import com.example.demo.entity.bullet.HeavyBulletConfig;
import com.example.demo.util.Vector;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

public class BossPlane extends Plane {
	private static final Vector INITIAL_POSITION = new Vector(84.0, -492.0);
	public static final int INITIAL_HEALTH = 150;
	private static final double MIN_MILLISECONDS_PER_FIRE = 500.0;
	private static final double MAX_MILLISECONDS_PER_FIRE = 2000.0;
	private static final Vector DIRECTION = Vector.DOWN;
	private static final double SPEED = 0.36;
	private static final double MAX_Y = -123.0;
	private static final Vector BULLET_OFFSET = new Vector(428.0, 492.0);

	private final Controller controller;
	private final BulletConfig bulletConfig;

	private double millisecondsBeforeNextShot;

	public BossPlane(Controller controller) {
		super(controller, new BossPlaneImageData(), INITIAL_POSITION, INITIAL_HEALTH);

		this.controller = controller;
		this.bulletConfig = new HeavyBulletConfig(this, null, BULLET_OFFSET);

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
		Vector shootingPosition = getPosition().add(BULLET_OFFSET);
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
