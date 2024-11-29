package com.example.demo.entity.player;

import com.example.demo.controller.GameController;
import com.example.demo.controller.GameLoop;
import com.example.demo.entity.FighterPlane;
import com.example.demo.controller.Input;
import com.example.demo.signal.Signal;
import com.example.demo.util.Vector;
import javafx.geometry.Bounds;

import static com.example.demo.Main.SCREEN_WIDTH;
import static com.example.demo.Main.SCREEN_HEIGHT;

public class PlayerPlane extends FighterPlane {
	public static final int HEALTH = 5;

	private static final String IMAGE_NAME = "player_plane.png";
	private static final int IMAGE_HEIGHT = 30;
	private static final int MIN_FRAMES_PER_FIRE = 15;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final double SPEED = 0.5;
	private static final Vector PROJECTILE_POSITION_OFFSET = new Vector(115, 20);

	private final GameController gameController;
	private final Signal enemyPlaneDestroyed;
	private final Signal damageTaken;

	private int numberOfKills = 0;
	private int framesSinceLastShot = 0;

	public PlayerPlane(GameController gameController) {
		super(gameController, IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);

		this.gameController = gameController;
		this.enemyPlaneDestroyed = new Signal();
		this.damageTaken = new Signal();
	}

	public Signal getEnemyPlaneDestroyedSignal() {
		return enemyPlaneDestroyed;
	}

	public Signal getDamageTakenSignal() {
		return damageTaken;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	@Override
	public void updatePosition() {
		moveWithinBounds(Input.getInputMoveDirection());
	}

	private void moveWithinBounds(Vector direction) {
		Vector scaledVector = direction.duplicate();
		scaledVector.multiply(PlayerPlane.SPEED * GameLoop.MILLISECOND_DELAY);

		double newTranslateX = getTranslateX() + scaledVector.getX();
		double newTranslateY = getTranslateY() + scaledVector.getY();

		Bounds bounds = getBoundsInParent();

		double newGlobalX = newTranslateX + getLayoutX();
		double newGlobalY = newTranslateY + getLayoutY();

		if (newGlobalX >= 0 && newGlobalX <= SCREEN_WIDTH * 0.25 - bounds.getWidth())
			setTranslateX(newTranslateX);

		if (newGlobalY >= 0 && newGlobalY <= SCREEN_HEIGHT - bounds.getHeight())
			setTranslateY(newTranslateY);
	}

	@Override
	public boolean canFireProjectile() {
		if (framesSinceLastShot >= MIN_FRAMES_PER_FIRE) {
			if (Input.isFireActive())
				return true;
		}

		framesSinceLastShot++;

		return false;
	}

    @Override
    public void fireProjectile() {
		double projectilePosX = PROJECTILE_POSITION_OFFSET.getX() + getTranslateX() + getLayoutX();
		double projectilePosY = PROJECTILE_POSITION_OFFSET.getY() + getTranslateY() + getLayoutY();
		PlayerProjectile projectile = new PlayerProjectile(gameController, projectilePosX, projectilePosY);

		projectile.getEnemyPlaneDestroyedSignal().connect(this, "incrementKillCount");

		projectile.addToScene();

		framesSinceLastShot = 0;
    }

	public void incrementKillCount() {
		numberOfKills++;
		enemyPlaneDestroyed.emit();
	}

	@Override
	protected void takeDamage(int damageAmount) {
		super.takeDamage(damageAmount);
		damageTaken.emit(damageAmount);
	}

	@Override
	public void onSceneReset() {
		setHealth(HEALTH);
	}

	@Override
	public boolean isFriendly() {
		return true;
	}
}
