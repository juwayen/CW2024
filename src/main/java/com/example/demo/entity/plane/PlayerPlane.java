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

/**
 * Represents the player's controllable plane in the game.
 */
public class PlayerPlane extends Plane {
	private static final double ENTER_LEVEL_FINAL_Y = 706.75;
	private static final Vector EXIT_LEVEL_FINAL_POSITION = new Vector(460.0, -64.0);
	private static final Vector MIN_POS = new Vector(0.0, 492.5);
	private static final Vector MAX_POS = new Vector(920.0, 921.0);
	private static final Vector BULLET_DIRECTION = new Vector(0.0, -1.0);
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

	/**
	 * Getter method for the health updated {@link Signal}.
	 *
	 * @return The {@link Signal} corresponding to health updates.
	 */
	public Signal getHealthUpdatedSignal() {
		return healthUpdated;
	}

	/**
	 * Getter method for the entered level {@link Signal}.
	 *
	 * @return The {@link Signal} corresponding to the event of entering a new level.
	 */
	public Signal getEnteredLevelSignal() {
		return enteredLevel;
	}

	/**
	 * Getter method for the exited level {@link Signal}.
	 *
	 * @return The {@link Signal} corresponding to the event of exiting a level.
	 */
	public Signal getExitedLevelSignal() {
		return exitedLevel;
	}

	/**
	 * Setter method for the health.
	 * Emits the health updated {@link Signal}.
	 *
	 * @param health The new health value to be set.
	 */
	@Override
	public void setHealth(int health) {
		super.setHealth(health);
		healthUpdated.emit();
	}

	/**
	 * Constructs a {@link PlayerPlane} instance.
	 * Invokes {@link #initializeBulletConfigs()}.
	 */
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

	/**
	 * Initializes the {@link BulletConfig} objects for the {@link PlayerPlane}.
	 * Sets the direction and assigns the shooter for both the base and upgraded configurations.
	 */
	private void initializeBulletConfigs() {
		baseBulletConfig.setDirection(BULLET_DIRECTION);
		baseBulletConfig.setShooter(this);

		upgradedBulletConfig.setDirection(BULLET_DIRECTION);
		upgradedBulletConfig.setShooter(this);
	}

	/**
	 * Calculates and returns the center position of the {@link PlayerPlane}.
	 *
	 * @return A {@link Vector} representing the center position.
	 */
	public Vector getCenterPosition() {
		return getPosition().add(ImageUtils.getImageCenterOffset(getImage()));
	}

	/**
	 * Updates the {@link PlayerPlane}'s {@link BulletConfig} to the upgraded version.
	 */
	public void powerup() {
		planeData.setBulletConfig(upgradedBulletConfig);
		millisecondsSincePowerup = 0;
	}

	/**
	 * Update method that invokes {@link #updatePowerup()}.
	 */
	@Override
	public void update() {
		super.update();
		updatePowerup();
	}

	/**
	 * Checks if the powerup should be reset.
	 * If the elapsed time exceeds the allowed maximum duration for the powerup, the {@link BulletConfig} is reset to the base version.
	 */
	private void updatePowerup() {
		if (planeData.getBulletConfig() != upgradedBulletConfig)
			return;

		millisecondsSincePowerup += MILLISECOND_DELAY;

		if (millisecondsSincePowerup > MAX_MILLISECONDS_WITH_POWERUP)
			planeData.setBulletConfig(baseBulletConfig);
	}

	/**
	 * Resets the health of the {@link PlayerPlane} to its initial value.
	 */
	public void resetHealth() {
		setHealth(planeData.getHealth());
	}

	/**
	 * Updates the position of the {@link PlayerPlane} by invoking {@link #moveWithinBounds(Vector)}.
	 */
	@Override
	public void updatePosition() {
		if (!isControllable)
			return;

		moveWithinBounds(inputService.getInputMoveDirection());
	}

	/**
	 * Updates the position of the {@link PlayerPlane} based on the given direction.
	 * Makes use of {@link Vector#clamped(Vector, Vector)}.
	 *
	 * @param direction A {@link Vector} representing the direction of the movement.
	 */
	private void moveWithinBounds(Vector direction) {
		Vector scaledVector = direction.multiply(speed * MILLISECOND_DELAY);
		Vector newPosition = getPosition().add(scaledVector);
		setPosition(newPosition.clamped(MIN_POS, MAX_POS));
	}

	/**
	 * Determines whether the {@link PlayerPlane} is allowed to fire based on the time elapsed since the last shot and its current controllable state.
	 */
	@Override
	public boolean canFire() {
		millisecondsSinceLastShot += MILLISECOND_DELAY;

		if (!isControllable)
			return false;

		if (millisecondsSinceLastShot >= minMillisecondsPerFire)
            return inputService.isFireActive();

		return false;
	}

	/**
	 * Fires a {@link Bullet} from the {@link PlayerPlane} based on the {@link BulletConfig}.
	 */
    @Override
    public void fire() {
		Bullet bullet = new Bullet(planeData.getBulletConfig());

		bullet.addToScene();

		millisecondsSinceLastShot = 0;
    }

	/**
	 * Resets the state of the {@link PlayerPlane} when the scene is reset.
	 */
	@Override
	public void onSceneReset() {
		setPosition(initialPosition);
		setHealth(initialHealth);
		planeData.setBulletConfig(baseBulletConfig);
	}

	/**
	 * Marks {@link PlayerPlane} instances as friendly.
	 *
	 * @return {@code true}.
	 */
	@Override
	public boolean isFriendly() {
		return true;
	}

	/**
	 * Plays the level enter animation for the {@link PlayerPlane}.
	 */
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

	/**
	 * Plays the level exit animation for the {@link PlayerPlane}.
	 */
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
