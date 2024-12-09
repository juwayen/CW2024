package com.example.demo.entity.plane;

import com.example.demo.Controller;
import com.example.demo.entity.Entity;
import com.example.demo.entity.state.*;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Signal;
import com.example.demo.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class Plane extends Entity {
	private static final int FRAMES_PER_IMAGE = 4;

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

	public Signal getDestroyedSignal() {
		return destroyed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Timeline getMovingStraightTimeline() {
		return movingStraightTimeline;
	}

	public Timeline getMovingRightTimeline() {
		return movingRightTimeline;
	}

	public Timeline getMovingLeftTimeline() {
		return movingLeftTimeline;
	}

	public Timeline getDestroyedTimeline() {
		return destroyedTimeline;
	}

	public Plane(Controller controller, PlaneData planeData, Vector initialPosition, int health) {
		super(controller, planeData.getMovingStraightImages().get(0), initialPosition);

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

		this.health = health;
		this.oldPosition = new Vector();
		this.velocity = new Vector();

		initialize();
	}

	private void initialize() {
		initializeTimelines();
	}

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

	@Override
	protected void clearSignalsConnections() {
		super.clearSignalsConnections();
		destroyed.clearConnections();
	}

	@Override
	public void update() {
		updatePosition();
		updateFire();
		calculateVelocity();
		updateState();
	}

	protected abstract void updatePosition();

	protected void updateFire() {
		if (canFire())
			fire();
	}

	protected abstract boolean canFire();

	protected abstract void fire();

	private void calculateVelocity() {
		Vector newPosition = getPosition();

		velocity = newPosition.subtract(oldPosition);

		oldPosition = newPosition;
	}

	private void updateState() {
		double velocityX = velocity.getX();

		if (velocityX > 0)
			stateMachine.changeState(movingRightState);
		else if (velocityX < 0)
			stateMachine.changeState(movingLeftState);
		else
			stateMachine.changeState(movingStraightState);
	}

	public void takeDamage(int damageAmount) {
		setHealth(health - damageAmount);

		if (health <= 0) {
			disableInteraction();
			stateMachine.changeState(destroyedState);
		}
	}

	public abstract boolean isFriendly();
}
