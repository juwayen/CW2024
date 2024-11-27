package com.example.demo.entity;

import java.util.*;

public class Boss extends FighterPlane {
	private static final String IMAGE_NAME = "bossplane.png";
	private static final double INITIAL_X_POSITION = 1040;
	private static final double INITIAL_Y_POSITION = 300;
	private static final double PROJECTILE_Y_POSITION_OFFSET = 0.0;
	private static final double BOSS_SHIELD_PROBABILITY = 0.25;
	private static final int IMAGE_HEIGHT = 80;
	private static final int VERTICAL_VELOCITY = 8;
	private static final int HEALTH = 50;
	private static final int MOVE_FREQUENCY_PER_CYCLE = 5;
	private static final int ZERO = 0;
	private static final int MAX_FRAMES_WITH_SAME_MOVE = 10;
	private static final int MIN_FRAMES_PER_FIRE = 60;
	private static final int MAX_FRAMES_PER_FIRE = 150;
	private static final int Y_POSITION_UPPER_BOUND = 0;
	private static final int Y_POSITION_LOWER_BOUND = 600;
	private static final int FRAMES_PER_SHIELD_CHANCE = 300;
	private static final int MAX_FRAMES_WITH_SHIELD = 90;

	private int framesBeforeNextShot = 0;
	private int framesBeforeNextShieldChance = 0;
	private final List<Integer> movePattern;
	private boolean isShielded;
	private int consecutiveMovesInSameDirection;
	private int indexOfCurrentMove;
	private int framesWithShieldActivated;

	public Boss() {
		super(IMAGE_NAME, IMAGE_HEIGHT, INITIAL_X_POSITION, INITIAL_Y_POSITION, HEALTH);
		movePattern = new ArrayList<>();
		consecutiveMovesInSameDirection = 0;
		indexOfCurrentMove = 0;
		framesWithShieldActivated = 0;
		isShielded = false;
		initializeMovePattern();
	}

	public boolean getIsShielded() {
		return isShielded;
	}

	@Override
	public void updatePosition() {
		double initialTranslateY = getTranslateY();
		moveVertically(getNextMove());
		double currentPosition = getLayoutY() + getTranslateY();
		if (currentPosition < Y_POSITION_UPPER_BOUND || currentPosition > Y_POSITION_LOWER_BOUND) {
			setTranslateY(initialTranslateY);
		}
	}
	
	@Override
	public void updateEntity() {
		updatePosition();
		updateShield();
	}

	@Override
	public EntityDestructible fireProjectile() {
		return bossFiresInCurrentFrame() ? new BossProjectile(getProjectileInitialPosition()) : null;
	}
	
	@Override
	public void takeDamage() {
		if (!isShielded) {
			super.takeDamage();
		}
	}

	private void initializeMovePattern() {
		for (int i = 0; i < MOVE_FREQUENCY_PER_CYCLE; i++) {
			movePattern.add(VERTICAL_VELOCITY);
			movePattern.add(-VERTICAL_VELOCITY);
			movePattern.add(ZERO);
		}

		Collections.shuffle(movePattern);
	}

	private void updateShield() {
		if (isShielded)
			framesWithShieldActivated++;
		else if (shieldShouldBeActivated())
			activateShield();

		if (shieldExhausted())
			deactivateShield();
	}

	private int getNextMove() {
		int currentMove = movePattern.get(indexOfCurrentMove);
		consecutiveMovesInSameDirection++;
		if (consecutiveMovesInSameDirection == MAX_FRAMES_WITH_SAME_MOVE) {
			Collections.shuffle(movePattern);
			consecutiveMovesInSameDirection = 0;
			indexOfCurrentMove++;
		}
		if (indexOfCurrentMove == movePattern.size()) {
			indexOfCurrentMove = 0;
		}
		return currentMove;
	}

	private boolean bossFiresInCurrentFrame() {
		if (framesBeforeNextShot <= 0) {
			framesBeforeNextShot = (int)(Math.random() * (MAX_FRAMES_PER_FIRE - MIN_FRAMES_PER_FIRE + 1)) + MIN_FRAMES_PER_FIRE;
			return true;
		}

		framesBeforeNextShot--;

		return false;
	}

	private double getProjectileInitialPosition() {
		return getLayoutY() + getTranslateY() + PROJECTILE_Y_POSITION_OFFSET;
	}

	private boolean shieldShouldBeActivated() {
		if (framesBeforeNextShieldChance <= 0) {
			framesBeforeNextShieldChance = FRAMES_PER_SHIELD_CHANCE;
			return Math.random() < BOSS_SHIELD_PROBABILITY;
		}

		framesBeforeNextShieldChance--;

		return false;
	}

	private boolean shieldExhausted() {
		return framesWithShieldActivated >= MAX_FRAMES_WITH_SHIELD;
	}

	private void activateShield() {
		isShielded = true;
	}

	private void deactivateShield() {
		isShielded = false;
		framesWithShieldActivated = 0;
	}
}
