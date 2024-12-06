package com.example.demo.entity.plane;

import com.example.demo.GameController;
import com.example.demo.entity.Entity;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Signal;
import com.example.demo.util.Vector;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class Plane extends Entity {
	private static final int FRAMES_PER_IMAGE = 4;

	private final PlaneImageData planeImageData;
	private final Signal destroyed;
	private final Timeline movingStraightTimeline;
	private final Timeline movingRightTimeline;
	private final Timeline movingLeftTimeline;
	private final Timeline destroyedTimeline;

	private int health;

	public Signal getDestroyedSignal() {
		return destroyed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Plane(GameController gameController, PlaneImageData planeImageData, Vector initialPosition, int health) {
		super(gameController, planeImageData.getMovingStraightImages().get(0), initialPosition);

		this.planeImageData = planeImageData;
		this.destroyed = new Signal();
		this.movingStraightTimeline = new Timeline();
		this.movingRightTimeline = new Timeline();
		this.movingLeftTimeline = new Timeline();
		this.destroyedTimeline = new Timeline();

		this.health = health;

		initialize();
	}

	private void initialize() {
		initializeTimelines();
	}

	private void initializeTimelines() {
		movingStraightTimeline.setCycleCount(Timeline.INDEFINITE);
		ImageUtils.initializeTimeline(movingStraightTimeline, planeImageData.getMovingStraightImages(), this, FRAMES_PER_IMAGE);
		movingStraightTimeline.play();

		movingRightTimeline.setCycleCount(Timeline.INDEFINITE);
		ImageUtils.initializeTimeline(movingRightTimeline, planeImageData.getMovingRightImages(), this, FRAMES_PER_IMAGE);

		movingLeftTimeline.setCycleCount(Timeline.INDEFINITE);
		ImageUtils.initializeTimeline(movingLeftTimeline, planeImageData.getMovingLeftImages(), this, FRAMES_PER_IMAGE);

		Vector movingImageCenterOffset = ImageUtils.getImageCenterOffset(getImage());
		Vector destroyedImageCenterOffset = ImageUtils.getImageCenterOffset(planeImageData.getDestroyedImages().get(0));
		Vector destroyedImageOffset = movingImageCenterOffset.subtract(destroyedImageCenterOffset);
		KeyFrame keyFrame = new KeyFrame(Duration.ZERO, event -> setPosition(getPosition().add(destroyedImageOffset)));
		destroyedTimeline.getKeyFrames().add(keyFrame);

		ImageUtils.initializeTimeline(destroyedTimeline, planeImageData.getDestroyedImages(), this, FRAMES_PER_IMAGE);

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
	}

	protected abstract void updatePosition();

	protected void updateFire() {
		if (canFire())
			fire();
	}

	protected abstract boolean canFire();

	protected abstract void fire();

	public void takeDamage(int damageAmount) {
		health -= damageAmount;

		if (health <= 0) {
			disableInteraction();
			movingStraightTimeline.stop();
			destroyedTimeline.play();
		}
	}

	public abstract boolean isFriendly();

	protected void playMovingStraightTimeline() {
		movingStraightTimeline.play();
	}
}
