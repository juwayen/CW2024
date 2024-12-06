package com.example.demo.entity.bullet;

import com.example.demo.GameController;
import com.example.demo.service.*;
import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.Plane;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;

import static com.example.demo.Main.*;

public class Bullet extends Entity {
	private static final int FRAMES_PER_IMAGE = 4;

	private final BulletConfig bulletConfig;
	private final boolean isFriendly;
	private final Vector direction;
	private final double speed;
	private final int damage;
	private final Timeline destroyedTimeline;

	public Bullet(GameController gameController, BulletConfig bulletConfig) {
		super(gameController, bulletConfig.getImage(), bulletConfig.getInitialPosition());

		this.bulletConfig = bulletConfig;
		this.isFriendly = bulletConfig.getShooter().isFriendly();
		this.direction = bulletConfig.getDirection();
		this.speed = bulletConfig.getSpeed();
		this.damage = bulletConfig.getDamage();
		this.destroyedTimeline = new Timeline();

		initialize();
	}

	private void initialize() {
		initializeTimelines();
	}

	private void initializeTimelines() {
		ImageUtils.initializeTimeline(destroyedTimeline, bulletConfig.getDestroyedImages(), this, FRAMES_PER_IMAGE);
		destroyedTimeline.setOnFinished(event -> {
			disableVisuals();
			emitRemovedSignal();
		});
	}

	@Override
	public void update() {
		updatePosition();
		updateOutOfBoundsCheck();
	}

	private void updatePosition() {
		move(direction, speed);
	}

	private void updateOutOfBoundsCheck() {
		if (isOutOfBounds())
			removeFromScene();
	}

	private boolean isOutOfBounds() {
		Bounds bounds = getBoundsInParent();

		return  bounds.getMaxX() < 0 ||
				bounds.getMaxY() < 0 ||
				bounds.getMinX() > GAME_WIDTH ||
				bounds.getMinY() > GAME_HEIGHT;
	}

	@Override
	public void onCollision(Collidable collidable) {
		if (!(collidable instanceof Plane plane))
			return;

        if (isFriendly == plane.isFriendly())
			return;

		plane.takeDamage(damage);

		disableInteraction();
		destroyedTimeline.play();
		Vector movingImageCenterOffset = ImageUtils.getImageCenterOffset(getImage());
		Vector destroyedImageCenterOffset = ImageUtils.getImageCenterOffset(bulletConfig.getDestroyedImages().get(0));
		Vector destroyedImageOffset = movingImageCenterOffset.subtract(destroyedImageCenterOffset);
		setPosition(getPosition().add(destroyedImageOffset));
	}
}
