package com.example.demo.entity.bullet;

import com.example.demo.Controller;
import com.example.demo.entity.state.BulletDestroyedState;
import com.example.demo.entity.state.BulletMovingState;
import com.example.demo.entity.state.EntityStateMachine;
import com.example.demo.service.*;
import com.example.demo.entity.Entity;
import com.example.demo.entity.plane.Plane;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Bounds;
import javafx.util.Duration;

import static com.example.demo.Main.*;

public class Bullet extends Entity {
	private static final int FRAMES_PER_IMAGE = 4;

	private final BulletConfig bulletConfig;
	private final boolean isFriendly;
	private final Vector direction;
	private final double speed;
	private final int damage;
	private final Timeline movingTimeline;
	private final Timeline destroyedTimeline;
	private final EntityStateMachine stateMachine;
	private final BulletMovingState movingState;
	private final BulletDestroyedState destroyedState;

	public Timeline getMovingTimeline() {
		return movingTimeline;
	}

	public Timeline getDestroyedTimeline() {
		return destroyedTimeline;
	}

	public Bullet(Controller controller, BulletConfig bulletConfig) {
		super(controller, bulletConfig.getImage(), bulletConfig.getInitialPosition());

		this.bulletConfig = bulletConfig;
		this.isFriendly = bulletConfig.getShooter().isFriendly();
		this.direction = bulletConfig.getDirection();
		this.speed = bulletConfig.getSpeed();
		this.damage = bulletConfig.getDamage();
		this.movingTimeline = new Timeline();
		this.destroyedTimeline = new Timeline();
		this.stateMachine = new EntityStateMachine(this);
		this.movingState = new BulletMovingState();
		this.destroyedState = new BulletDestroyedState();

		initialize();
	}

	private void initialize() {
		initializeTimelines();
	}

	private void initializeTimelines() {
		ImageUtils.addImageToTimeline(movingTimeline, bulletConfig.getImage(),this);

		Vector movingImageCenterOffset = ImageUtils.getImageCenterOffset(getImage());
		Vector destroyedImageCenterOffset = ImageUtils.getImageCenterOffset(bulletConfig.getDestroyedImages().get(0));
		Vector destroyedImageOffset = movingImageCenterOffset.subtract(destroyedImageCenterOffset);
		KeyFrame keyFrame = new KeyFrame(Duration.ZERO, event -> setPosition(getPosition().add(destroyedImageOffset)));
		destroyedTimeline.getKeyFrames().add(keyFrame);

		ImageUtils.addImagesToTimeline(destroyedTimeline, bulletConfig.getDestroyedImages(), this, FRAMES_PER_IMAGE);

		destroyedTimeline.setOnFinished(event -> {
			disableVisuals();
			emitRemovedSignal();
		});
	}

	@Override
	public void update() {
		updatePosition();
		updateOutOfBoundsCheck();
		updateState();
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

	private void updateState() {
		stateMachine.changeState(movingState);
	}

	@Override
	public void onCollision(Collidable collidable) {
		if (!(collidable instanceof Plane plane))
			return;

        if (isFriendly == plane.isFriendly())
			return;

		plane.takeDamage(damage);

		disableInteraction();
		stateMachine.changeState(destroyedState);
	}
}
