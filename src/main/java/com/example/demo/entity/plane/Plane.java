package com.example.demo.entity.plane;

import com.example.demo.entity.Entity;
import com.example.demo.entity.bullet.Bullet;
import com.example.demo.entity.state.*;
import com.example.demo.service.AudioService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Signal;
import com.example.demo.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.List;

/**
 * Abstract base class representing a plane in the game.
 */
public abstract class Plane extends Entity {
	private static final int FRAMES_PER_IMAGE = 4;

	private final AudioService audioService;
	private final PlaneData planeData;
	private final Signal destroyed;
	private final Timeline movingStraightTimeline;
	private final Timeline movingRightTimeline;
	private final Timeline movingLeftTimeline;
	private final Timeline destroyedTimeline;
	private final EntityStateMachine stateMachine;
	private final PlaneMovingStraightState movingStraightState;
	private final PlaneMovingRightState movingRightState;
	private final PlaneMovingLeftState movingLeftState;
	private final PlaneDestroyedState destroyedState;

	private int health;
	private Vector oldPosition;
	private Vector velocity;

	/**
	 * Getter method for the {@link PlaneData} object associated with this {@link Plane}.
	 *
	 * @return The {@link PlaneData} instance associated with this {@link Plane}.
	 */
	protected PlaneData getPlaneData() {
		return planeData;
	}

	/**
	 * Getter method for the destroyed {@link Signal} associated with the plane.
	 *
	 * @return The {@link Signal} instance that represents the destroyed {@link Signal} for the plane.
	 */
	public Signal getDestroyedSignal() {
		return destroyed;
	}

	/**
	 * Getter method for the current health.
	 *
	 * @return The current health.
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Setter method for the current health.
	 *
	 * @param health The new health value to be set.
	 */
	public void setHealth(int health) {
		this.health = health;
	}

	/**
	 * Getter method for the moving straight animation {@link Timeline}.
	 *
	 * @return The moving straight animation {@link Timeline}.
	 */
	public Timeline getMovingStraightTimeline() {
		return movingStraightTimeline;
	}

	/**
	 * Getter method for the moving right animation {@link Timeline}.
	 *
	 * @return The moving right animation {@link Timeline}.
	 */
	public Timeline getMovingRightTimeline() {
		return movingRightTimeline;
	}

	/**
	 * Getter method for the moving left animation {@link Timeline}.
	 *
	 * @return The moving left animation {@link Timeline}.
	 */
	public Timeline getMovingLeftTimeline() {
		return movingLeftTimeline;
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
	 * Constructs a {@link Plane} instance using the provided {@link PlaneData}.
	 * Initializes the animations.
	 *
	 * @param planeData The {@link PlaneData} object containing the properties for the {@link Plane}.
	 */
	public Plane(PlaneData planeData) {
		super(planeData.getMovingStraightImages().get(0), planeData.getInitialPosition());

		this.audioService = ServiceLocator.getAudioService();
		this.planeData = planeData;
		this.destroyed = new Signal();
		this.movingStraightTimeline = new Timeline();
		this.movingRightTimeline = new Timeline();
		this.movingLeftTimeline = new Timeline();
		this.destroyedTimeline = new Timeline();
		this.stateMachine = new EntityStateMachine(this);
		this.movingStraightState = new PlaneMovingStraightState();
		this.movingRightState = new PlaneMovingRightState();
		this.movingLeftState = new PlaneMovingLeftState();
		this.destroyedState = new PlaneDestroyedState();

		this.health = planeData.getHealth();
		this.oldPosition = new Vector();
		this.velocity = new Vector();

		initializeTimelines();
	}

	/**
	 * Initializes the {@link Timeline} for the animations using {@link ImageUtils#addImagesToTimeline(Timeline, List, ImageView, int)}.
	 */
	private void initializeTimelines() {
		movingStraightTimeline.setCycleCount(Timeline.INDEFINITE);
		ImageUtils.addImagesToTimeline(movingStraightTimeline, planeData.getMovingStraightImages(), this, FRAMES_PER_IMAGE);

		movingRightTimeline.setCycleCount(Timeline.INDEFINITE);
		ImageUtils.addImagesToTimeline(movingRightTimeline, planeData.getMovingRightImages(), this, FRAMES_PER_IMAGE);

		movingLeftTimeline.setCycleCount(Timeline.INDEFINITE);
		ImageUtils.addImagesToTimeline(movingLeftTimeline, planeData.getMovingLeftImages(), this, FRAMES_PER_IMAGE);

		Vector movingImageCenterOffset = ImageUtils.getImageCenterOffset(getImage());
		Vector destroyedImageCenterOffset = ImageUtils.getImageCenterOffset(planeData.getDestroyedImages().get(0));
		Vector destroyedImageOffset = movingImageCenterOffset.subtract(destroyedImageCenterOffset);
		KeyFrame keyFrame = new KeyFrame(Duration.ZERO, event -> setPosition(getPosition().add(destroyedImageOffset)));
		destroyedTimeline.getKeyFrames().add(keyFrame);

		ImageUtils.addImagesToTimeline(destroyedTimeline, planeData.getDestroyedImages(), this, FRAMES_PER_IMAGE);

		destroyedTimeline.setOnFinished(event -> {
			disableVisuals();
			destroyed.emit();
			emitRemovedSignal();
		});
	}

	/**
	 * Invokes {@link Signal#clearConnections()} on all {@link Signal}s.
	 */
	@Override
	protected void clearSignalsConnections() {
		super.clearSignalsConnections();
		destroyed.clearConnections();
	}

	/**
	 * Update method that invokes {@link #updatePosition()}, {@link #updateFire()}, {@link #calculateVelocity()}, and {@link #updateState()}.
	 */
	@Override
	public void update() {
		updatePosition();
		updateFire();
		calculateVelocity();
		updateState();
	}

	/**
	 * Handles the logic for updating the position of the plane.
	 */
	protected abstract void updatePosition();

	/**
	 * Handles the logic for firing a {@link Bullet}.
	 */
	protected void updateFire() {
		if (canFire()) {
			fire();
			audioService.playSound(AudioService.Sound.BULLET_FIRED);
		}
	}

	/**
	 * Determines whether the plane is allowed to fire a bullet based on current state or logic.
	 *
	 * @return {@code true} if the plane can fire, {@code false} otherwise.
	 */
	protected abstract boolean canFire();

	protected abstract void fire();

	private void calculateVelocity() {
		Vector newPosition = getPosition();

		velocity = newPosition.subtract(oldPosition);

		oldPosition = newPosition;
	}

	/**
	 * Updates the current {@link com.example.demo.entity.state.EntityState} by transitioning to the appropriate moving state based on the velocity.
	 */
	private void updateState() {
		double velocityX = velocity.getX();

		if (velocityX > 0)
			stateMachine.changeState(movingRightState);
		else if (velocityX < 0)
			stateMachine.changeState(movingLeftState);
		else
			stateMachine.changeState(movingStraightState);
	}

	/**
	 * Applies damage to the plane by reducing its health.
	 * If the health drops to zero or below, it invokes {@link Entity#disableInteraction()}, and transitions to {@link PlaneDestroyedState}.
	 *
	 * @param damageAmount The amount of damage to be applied.
	 */
	public void takeDamage(int damageAmount) {
		setHealth(health - damageAmount);

		if (health <= 0) {
			disableInteraction();
			stateMachine.changeState(destroyedState);
			audioService.playSound(AudioService.Sound.PLANE_DESTROYED);
		}
	}

	/**
	 * Determines whether the {@link Plane} is friendly or not.
	 *
	 * @return {@code true} if the {@link Plane} is friendly, {@code false} otherwise.
	 */
	public abstract boolean isFriendly();
}
