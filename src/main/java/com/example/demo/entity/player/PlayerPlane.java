package com.example.demo.entity.player;

import com.example.demo.controller.GameController;
import com.example.demo.entity.Collidable;
import com.example.demo.entity.FighterPlane;
import com.example.demo.controller.Input;
import com.example.demo.signal.Signal;
import com.example.demo.util.Vector;

import static com.example.demo.Main.OUTPUT_SCALE;
import static com.example.demo.controller.GameLoop.MILLISECOND_DELAY;

public class PlayerPlane extends FighterPlane {
	public static final int HEALTH = 5;

	private static final String IMAGE_NAME = "player_plane.png";
	private static final double MIN_MILLISECONDS_PER_FIRE = 66.667;
	private static final double INITIAL_X_POSITION = 460.0;
	private static final double INITIAL_Y_POSITION = 849.0;
	private static final double SPEED = 0.96;
	private static final Vector MIN_POS = new Vector(0, 0);
	private static final Vector MAX_POS = new Vector(920, 913);
	private static final Vector PROJECTILE_POSITION_OFFSET = new Vector(20, -64);

	private final GameController gameController;
	private final Signal damageTaken;

	private double millisecondsSinceLastShot = 0;

	public PlayerPlane(GameController gameController) {
		super(gameController, IMAGE_NAME, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);

		this.gameController = gameController;
		this.damageTaken = new Signal();
	}

	@Override
	public void onCollision(Collidable collidable) {
		super.onCollision(collidable);
	}

	public Signal getDamageTakenSignal() {
		return damageTaken;
	}

	@Override
	public void updatePosition() {
		moveWithinBounds(Input.getInputMoveDirection());
	}

	private void moveWithinBounds(Vector direction) {
		Vector scaledVector = direction.multiply(SPEED * MILLISECOND_DELAY);
		Vector newPosition = getPosition().add(scaledVector);
		setPosition(newPosition.clamped(MIN_POS, MAX_POS));
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

		projectile.addToScene();

		millisecondsSinceLastShot = 0;
    }

	@Override
	protected void takeDamage(int damageAmount) {
		super.takeDamage(damageAmount);
		damageTaken.emit();
	}

	@Override
	public void onSceneReset() {
		setPosition(new Vector(INITIAL_X_POSITION, INITIAL_Y_POSITION));
		setHealth(HEALTH);
	}

	@Override
	public boolean isFriendly() {
		return true;
	}
}
