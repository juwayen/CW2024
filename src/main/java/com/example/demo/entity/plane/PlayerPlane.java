package com.example.demo.entity.plane;

import com.example.demo.entity.bullet.*;
import com.example.demo.service.Collidable;
import com.example.demo.service.InputService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.util.Signal;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import static com.example.demo.service.UpdateService.MILLISECOND_DELAY;

public class PlayerPlane extends Plane {
	private static final double ENTER_LEVEL_FINAL_Y = 706.75;
	private static final Vector EXIT_LEVEL_FINAL_POSITION = new Vector(460.0, -64.0);
	private static final Vector MIN_POS = new Vector(0.0, 492.5);
	private static final Vector MAX_POS = new Vector(920.0, 921.0);
	private static final Vector BULLET_DIRECTION = Vector.UP;
	private static final double MAX_MILLISECONDS_WITH_POWERUP = 5000;

	private final InputService inputService;
	private final PlaneData planeData;
	private final Vector initialPosition;
	private final int initialHealth;
	private final double minMillisecondsPerFire;
	private final double speed;
	private final Signal healthUpdated;
	private final Signal enteredLevel;
	private final Signal exitedLevel;
	private final BulletConfig baseBulletConfig;
	private final BulletConfig upgradedBulletConfig;

	private boolean isControllable;
	private double millisecondsSinceLastShot;
	private double millisecondsSincePowerup;

	public Signal getHealthUpdatedSignal() {
		return healthUpdated;
	}

	public Signal getEnteredLevelSignal() {
		return enteredLevel;
	}

	public Signal getExitedLevelSignal() {
		return exitedLevel;
	}

	@Override
	public void setHealth(int health) {
		super.setHealth(health);
		healthUpdated.emit();
	}

	public PlayerPlane() {
		super(new PlayerData());

		this.inputService = ServiceLocator.getInputService();
		this.planeData = getPlaneData();
		this.initialPosition = planeData.getInitialPosition();
		this.initialHealth = planeData.getHealth();
		this.minMillisecondsPerFire = planeData.getMinMillisecondsPerFire();
		this.speed = planeData.getSpeed();
		this.healthUpdated = new Signal();
		this.exitedLevel = new Signal();
		this.enteredLevel = new Signal();
		this.baseBulletConfig = planeData.getBulletConfig();
		this.upgradedBulletConfig = new DoubleBulletConfig(planeData.getBulletConfig().getOffset());
		planeData.setBulletConfig(baseBulletConfig);

		this.isControllable = false;
		this.millisecondsSinceLastShot = 0.0;

		initializeBulletConfigs();
	}

	private void initializeBulletConfigs() {
		baseBulletConfig.setDirection(BULLET_DIRECTION);
		baseBulletConfig.setShooter(this);

		upgradedBulletConfig.setDirection(BULLET_DIRECTION);
		upgradedBulletConfig.setShooter(this);
	}

	public Vector getCenterPosition() {
		return getPosition().add(ImageUtils.getImageCenterOffset(getImage()));
	}

	public void powerup() {
		planeData.setBulletConfig(upgradedBulletConfig);
		millisecondsSincePowerup = 0;
	}

	@Override
	public void update() {
		super.update();
		updatePowerup();
	}

	private void updatePowerup() {
		if (planeData.getBulletConfig() != upgradedBulletConfig)
			return;

		millisecondsSincePowerup += MILLISECOND_DELAY;

		if (millisecondsSincePowerup > MAX_MILLISECONDS_WITH_POWERUP)
			planeData.setBulletConfig(baseBulletConfig);
	}

	public void resetHealth() {
		setHealth(planeData.getHealth());
	}

	@Override
	public void onCollision(Collidable collidable) {
		super.onCollision(collidable);
	}

	@Override
	public void updatePosition() {
		if (!isControllable)
			return;

		moveWithinBounds(inputService.getInputMoveDirection());
	}

	private void moveWithinBounds(Vector direction) {
		Vector scaledVector = direction.multiply(speed * MILLISECOND_DELAY);
		Vector newPosition = getPosition().add(scaledVector);
		setPosition(newPosition.clamped(MIN_POS, MAX_POS));
	}

	@Override
	public boolean canFire() {
		millisecondsSinceLastShot += MILLISECOND_DELAY;

		if (!isControllable)
			return false;

		if (millisecondsSinceLastShot >= minMillisecondsPerFire)
            return inputService.isFireActive();

		return false;
	}

    @Override
    public void fire() {
		Bullet bullet = new Bullet(planeData.getBulletConfig());

		bullet.addToScene();

		millisecondsSinceLastShot = 0;
    }

	@Override
	public void onSceneReset() {
		setPosition(initialPosition);
		setHealth(initialHealth);
		planeData.setBulletConfig(baseBulletConfig);
	}

	@Override
	public boolean isFriendly() {
		return true;
	}

	public void playEnterTransition() {
		isControllable = false;

		Vector finalPosition = new Vector(initialPosition.getX(), ENTER_LEVEL_FINAL_Y);
		double duration = initialPosition.distanceTo(finalPosition) / speed;

		TranslateTransition transition = new TranslateTransition();
		transition.setNode(this);
		transition.setDuration(Duration.millis(duration));
		transition.setFromY(getPosition().getY());
		transition.setToY(ENTER_LEVEL_FINAL_Y);
		transition.setOnFinished(event -> {
			isControllable = true;
			enteredLevel.emit();
		});

		transition.play();
	}

	public void playExitTransition() {
		isControllable = false;

		double duration = getPosition().distanceTo(EXIT_LEVEL_FINAL_POSITION) / speed;

		TranslateTransition transition = new TranslateTransition();
		transition.setNode(this);
		transition.setDuration(Duration.millis(duration));
		transition.setFromX(getPosition().getX());
		transition.setToX(EXIT_LEVEL_FINAL_POSITION.getX());
		transition.setFromY(getPosition().getY());
		transition.setToY(EXIT_LEVEL_FINAL_POSITION.getY());
		transition.setOnFinished(event -> exitedLevel.emit());

		transition.play();
	}
}
