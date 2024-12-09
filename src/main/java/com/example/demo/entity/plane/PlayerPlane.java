package com.example.demo.entity.plane;

import com.example.demo.Controller;
import com.example.demo.entity.bullet.*;
import com.example.demo.service.Collidable;
import com.example.demo.service.InputService;
import com.example.demo.service.ServiceLocator;
import com.example.demo.util.Signal;
import com.example.demo.util.ImageUtils;
import com.example.demo.util.Vector;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

import static com.example.demo.service.GameLoopService.MILLISECOND_DELAY;

public class PlayerPlane extends Plane {
	public static final int INITIAL_HEALTH = 10;

	private static final double MIN_MILLISECONDS_PER_FIRE = 66.667;
	private static final Vector INITIAL_POSITION = new Vector(460.0, 985.0);
	private static final double ENTER_LEVEL_FINAL_Y = 706.75;
	private static final Vector EXIT_LEVEL_FINAL_POSITION = new Vector(460.0, -64.0);
	private static final double SPEED = 0.96;
	private static final Vector MIN_POS = new Vector(0.0, 492.5);
	private static final Vector MAX_POS = new Vector(920.0, 921.0);
	private static final Vector BULLET_DIRECTION = Vector.UP;
	private static final Vector BULLET_OFFSET = new Vector(52.0, 0.0);

	private final Controller controller;
	private final Signal healthUpdated;
	private final Signal enteredLevel;
	private final Signal exitedLevel;
	private final InputService inputService;
	private final BulletConfig singleBulletConfig;
	private final BulletConfig doubleBulletConfig;

	private boolean isControllable;
	private BulletConfig bulletConfig;
	private double millisecondsSinceLastShot;

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

	public PlayerPlane(Controller controller) {
		super(controller, new PlayerData(), INITIAL_POSITION, INITIAL_HEALTH);

		this.controller = controller;
		this.healthUpdated = new Signal();
		this.exitedLevel = new Signal();
		this.enteredLevel = new Signal();
		this.inputService = ServiceLocator.getInputService();
		this.singleBulletConfig = new SingleBulletConfig(BULLET_OFFSET);
		singleBulletConfig.setShooter(this);
		singleBulletConfig.setDirection(BULLET_DIRECTION);
		this.doubleBulletConfig = new DoubleBulletConfig(BULLET_OFFSET);
		doubleBulletConfig.setShooter(this);
		doubleBulletConfig.setDirection(BULLET_DIRECTION);

		this.isControllable = false;
		this.bulletConfig = singleBulletConfig;
		this.millisecondsSinceLastShot = 0.0;
	}

	public Vector getCenterPosition() {
		return getPosition().add(ImageUtils.getImageCenterOffset(getImage()));
	}

	public void upgradeBullet() {
		bulletConfig = doubleBulletConfig;
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
		Vector scaledVector = direction.multiply(SPEED * MILLISECOND_DELAY);
		Vector newPosition = getPosition().add(scaledVector);
		setPosition(newPosition.clamped(MIN_POS, MAX_POS));
	}

	@Override
	public boolean canFire() {
		millisecondsSinceLastShot += MILLISECOND_DELAY;

		if (!isControllable)
			return false;

		if (millisecondsSinceLastShot >= MIN_MILLISECONDS_PER_FIRE)
            return inputService.isFireActive();

		return false;
	}

    @Override
    public void fire() {
		Bullet bullet = new Bullet(controller, bulletConfig);

		bullet.addToScene();

		millisecondsSinceLastShot = 0;
    }

	@Override
	public void onSceneReset() {
		setPosition(INITIAL_POSITION);
		setHealth(INITIAL_HEALTH);
		bulletConfig = singleBulletConfig;
	}

	@Override
	public boolean isFriendly() {
		return true;
	}

	public void playEnterTransition() {
		isControllable = false;

		Vector finalPosition = new Vector(INITIAL_POSITION.getX(), ENTER_LEVEL_FINAL_Y);
		double duration = INITIAL_POSITION.distanceTo(finalPosition) / SPEED;

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

		double duration = getPosition().distanceTo(EXIT_LEVEL_FINAL_POSITION) / SPEED;

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
