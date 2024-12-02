package com.example.demo.entity.player;

import com.example.demo.controller.GameController;
import com.example.demo.entity.FighterPlane;
import com.example.demo.controller.Input;
import com.example.demo.signal.Signal;
import com.example.demo.util.Vector;

import static com.example.demo.controller.GameLoop.MILLISECOND_DELAY;
import static com.example.demo.util.ImageUtils.OUTPUT_SCALE;

public class PlayerPlane extends FighterPlane {
	public static final int HEALTH = 5;

	private static final String IMAGE_NAME = "player_plane.png";
	private static final double MIN_MILLISECONDS_PER_FIRE = 66.667;
	private static final double INITIAL_X_POSITION = 451.0;
	private static final double INITIAL_Y_POSITION = 849.0;
	private static final double MIN_X = 0.0;
	private static final double MAX_X = 904.0;
	private static final double MIN_Y = 0.0;
	private static final double MAX_Y = 913.0;
	private static final double SPEED = 0.96;
	private static final Vector PROJECTILE_POSITION_OFFSET = new Vector(20, -64);

	private final GameController gameController;
	private final Signal enemyPlaneDestroyed;
	private final Signal damageTaken;

	private double millisecondsSinceLastShot = 0;
	private int numberOfKills = 0;

	public PlayerPlane(GameController gameController) {
		super(gameController, IMAGE_NAME, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);

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
		Vector scaledVector = direction.multiply(SPEED / OUTPUT_SCALE * MILLISECOND_DELAY);

		double newTranslateX = getTranslateX() + scaledVector.getX();
		double newTranslateY = getTranslateY() + scaledVector.getY();

		newTranslateX = Math.max(MIN_X / OUTPUT_SCALE, Math.min(newTranslateX, MAX_X / OUTPUT_SCALE));
		newTranslateY = Math.max(MIN_Y / OUTPUT_SCALE, Math.min(newTranslateY, MAX_Y / OUTPUT_SCALE));

		setTranslateX(newTranslateX);
		setTranslateY(newTranslateY);
	}

	@Override
	public boolean canFireProjectile() {
		millisecondsSinceLastShot += MILLISECOND_DELAY;

		if (millisecondsSinceLastShot >= MIN_MILLISECONDS_PER_FIRE)
            return Input.isFireActive();

		return false;
	}

    @Override
    public void fireProjectile() {
		double projectilePosX = PROJECTILE_POSITION_OFFSET.getX() + getTranslateX() * OUTPUT_SCALE;
		double projectilePosY = PROJECTILE_POSITION_OFFSET.getY() + getTranslateY() * OUTPUT_SCALE;
		PlayerProjectile projectile = new PlayerProjectile(gameController, projectilePosX, projectilePosY);

		projectile.getEnemyPlaneDestroyedSignal().connect(this::incrementKillCount);

		projectile.addToScene();

		millisecondsSinceLastShot = 0;
    }

	private void incrementKillCount() {
		numberOfKills++;
		enemyPlaneDestroyed.emit();
	}

	@Override
	protected void takeDamage(int damageAmount) {
		super.takeDamage(damageAmount);
		damageTaken.emit();
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
