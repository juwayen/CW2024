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
	public static final int INITIAL_HEALTH = 5;

	private static final double MIN_MILLISECONDS_PER_FIRE = 66.667;
	private static final Vector INITIAL_POSITION = new Vector(460.0, 985.0);
	private static final double ENTER_LEVEL_FINAL_Y = 793.0;
	private static final double SPEED = 0.96;
	private static final Vector MIN_POS = Vector.ZERO;
	private static final Vector MAX_POS = new Vector(920.0, 921.0);
	private static final Vector BULLET_DIRECTION = Vector.UP;
	private static final Vector BULLET_OFFSET = new Vector(52.0, 0.0);

	private final Controller controller;
	private final Signal enteredLevel;
	private final Signal damageTaken;
	private final BulletConfig bulletConfig;
	private final InputService inputService;

	private boolean isControllable;
	private double millisecondsSinceLastShot;

	public Signal getEnteredLevelSignal() {
		return enteredLevel;
	}

	public Signal getDamageTakenSignal() {
		return damageTaken;
	}

	public PlayerPlane(Controller controller) {
		super(controller, new PlayerPlaneImageData(), INITIAL_POSITION, INITIAL_HEALTH);

		this.controller = controller;
		this.enteredLevel = new Signal();
		this.damageTaken = new Signal();
		this.bulletConfig = new SingleBulletConfig(this, BULLET_DIRECTION, BULLET_OFFSET);
		this.inputService = ServiceLocator.getInputService();

		this.millisecondsSinceLastShot = 0.0;
	}

	public Vector getCenterPosition() {
		return getPosition().add(ImageUtils.getImageCenterOffset(getImage()));
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
    public void takeDamage(int damageAmount) {
		super.takeDamage(damageAmount);
		damageTaken.emit();
	}

	@Override
	public void onSceneReset() {
		setPosition(INITIAL_POSITION);
		setHealth(INITIAL_HEALTH);
	}

	@Override
	public boolean isFriendly() {
		return true;
	}

	public void playEnterTransition() {
		isControllable = false;

		TranslateTransition transition = new TranslateTransition();
		transition.setNode(this);
		transition.setDuration(Duration.seconds(1));
		transition.setFromY(getTranslateY());
		transition.setToY(ENTER_LEVEL_FINAL_Y);
		transition.setOnFinished(event -> {
			isControllable = true;
			enteredLevel.emit();
		});

		transition.play();
	}
}
