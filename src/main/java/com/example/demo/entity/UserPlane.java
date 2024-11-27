package com.example.demo.entity;

import com.example.demo.level.LevelParent;
import com.example.demo.manager.InputManager;
import javafx.scene.Group;

public class UserPlane extends FighterPlane {
	private static final String IMAGE_NAME = "userplane.png";
	private static final double Y_UPPER_BOUND = -280;
	private static final double Y_LOWER_BOUND = 340;
	private static final double INITIAL_X_POSITION = 5.0;
	private static final double INITIAL_Y_POSITION = 300.0;
	private static final int IMAGE_HEIGHT = 30;
	private static final int VERTICAL_VELOCITY = 12;
	private static final int PROJECTILE_X_POSITION = 110;
	private static final int PROJECTILE_Y_POSITION_OFFSET = 20;
	private static final int MIN_FRAMES_PER_FIRE = 5;
	private static final int INITIAL_HEALTH = 10;

	private static int userHealth = INITIAL_HEALTH;

	private final InputManager inputManager = InputManager.getInstance();
	private final LevelParent levelParent;

	private int framesSinceLastShot = 0;
	private int numberOfKills;

	public UserPlane(LevelParent levelParent) {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, userHealth);
		this.levelParent = levelParent;
	}
	
	@Override
	public void updatePosition() {
		double newTranslateY = getTranslateY();

		if (inputManager.isMoveUpActive())
			newTranslateY -= VERTICAL_VELOCITY;

		if (inputManager.isMoveDownActive())
			newTranslateY += VERTICAL_VELOCITY;

		if (isTranslateYInBounds(newTranslateY))
			setTranslateY(newTranslateY);
	}
	
	@Override
	public void updateEntity() {
		updatePosition();
		updateFire();
	}

    @Override
    public EntityDestructible fireProjectile() {
        return null;
    }

    @Override
	public void takeDamage() {
		userHealth--;

		if (userHealth <= 0)
			this.destroy();
	}

	@Override
	public int getHealth() {
		return userHealth;
	}

	private void fireUserProjectile() {
		EntityDestructible projectile = new UserProjectile(PROJECTILE_X_POSITION, getProjectileYPosition(PROJECTILE_Y_POSITION_OFFSET));

		levelParent.getRoot().getChildren().add(projectile);
		levelParent.getUserProjectiles().add(projectile);

		framesSinceLastShot = 0;
	}

	private void updateFire() {
		boolean canFireUserProjectile = framesSinceLastShot >= MIN_FRAMES_PER_FIRE;

		framesSinceLastShot++;

		if (inputManager.isFireActive()) {
			if (canFireUserProjectile)
				fireUserProjectile();
		}
	}

	private boolean isTranslateYInBounds(double translateY) {
		return translateY > Y_UPPER_BOUND && translateY < Y_LOWER_BOUND;
	}

	public int getNumberOfKills() {
		return numberOfKills;
	}

	public void incrementKillCount() {
		numberOfKills++;
	}
}
