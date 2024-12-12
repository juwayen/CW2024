package com.example.demo.entity.bullet;

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
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;

import static com.example.demo.Main.*;

/**
 * Represents a bullet in the game.
 */
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

	/**
	 * Getter method for the moving animation {@link Timeline}.
	 *
	 * @return The moving animation {@link Timeline}.
	 */
	public Timeline getMovingTimeline() {
		return movingTimeline;
	}

	/**
	 * Getter method for the destroyed animation {@link Timeline}.
	 *
	 * @return The destroyed animation {@link Timeline}.
	 */
	public Timeline getDestroyedTimeline() {
		return destroyedTimeline;
	}

	/**
	 * Constructs a {@link Bullet} instance using the provided {@link BulletConfig}.
	 * Initializes the animations.
	 *
	 * @param bulletConfig The {@link BulletConfig} object containing the properties.
	 */
	public Bullet(BulletConfig bulletConfig) {
		super(bulletConfig.getImage(), bulletConfig.getInitialPosition());

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

		initializeTimelines();
	}

	/**
	 * Initializes the {@link Timeline} for the animations using {@link ImageUtils#addImagesToTimeline(Timeline, List, ImageView, int)}.
	 */
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

	/**
	 * Update method that invokes {@link #updatePosition()}, {@link #updateOutOfBoundsCheck()}, and {@link #updateState()}.
	 */
	@Override
	public void update() {
		updatePosition();
		updateOutOfBoundsCheck();
		updateState();
	}

	/**
	 * Updates the position of the bullet by invoking the {@link Entity#move(Vector, double)} method.
	 */
	private void updatePosition() {
		move(direction, speed);
	}

	/**
	 * Checks if the bullet is out of bounds by invoking the {@link #isOutOfBounds()} method.
	 * If the bullet is out of bounds, it removes the bullet from the scene by calling {@link #removeFromScene()}.
	 */
	private void updateOutOfBoundsCheck() {
		if (isOutOfBounds())
			removeFromScene();
	}

	/**
	 * Checks if the current object is out of the bounds of the game area.
	 *
	 * @return true if the object is out of bounds, false otherwise.
	 */
	private boolean isOutOfBounds() {
		Bounds bounds = getBoundsInParent();

		return  bounds.getMaxX() < 0 ||
				bounds.getMaxY() < 0 ||
				bounds.getMinX() > GAME_WIDTH ||
				bounds.getMinY() > GAME_HEIGHT;
	}

	/**
	 * Updates the current {@link com.example.demo.entity.state.EntityState} by transitioning to the {@link BulletMovingState}.
	 */
	private void updateState() {
		stateMachine.changeState(movingState);
	}

	/**
	 * If the colliding {@link Plane} belongs to the other faction (friendly or enemy), it takes damage, and the {@link Bullet} transitions to the {@link BulletDestroyedState}.
	 *
	 * @param collidable The other {@link Collidable} object involved in the collision.
	 */
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
