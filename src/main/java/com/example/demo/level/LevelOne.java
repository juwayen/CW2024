package com.example.demo.level;

import com.example.demo.entity.EntityDestructible;
import com.example.demo.entity.enemy.EnemyPlane;

public class LevelOne extends LevelParent {
	private static final String BACKGROUND_IMAGE_NAME = "background_1.png";
	private static final int TOTAL_ENEMIES = 2;
	private static final int KILLS_TO_ADVANCE = 10;
	private static final double ENEMY_SPAWN_PROBABILITY = .20;

	public LevelOne(double screenWidth, double screenHeight) {
		super(BACKGROUND_IMAGE_NAME, screenWidth, screenHeight);
	}

	@Override
	protected void checkIfGameOver() {
		if (isPlayerDestroyed()) {
			loseGame();
		}
		else if (isKillTargetReached()) {
			stopLevel();
			levelWinSignal.emit();
		}
	}

	@Override
	protected void initializeFriendlyUnits() {
		getRoot().getChildren().add(getPlayer());
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getCurrentNumberOfEnemies();
		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			if (Math.random() < ENEMY_SPAWN_PROBABILITY) {
				double newEnemyInitialYPosition = Math.random() * getEnemyMaximumYPosition();
				EntityDestructible newEnemy = new EnemyPlane(getScreenWidth(), newEnemyInitialYPosition);
				addEnemyUnit(newEnemy);
			}
		}
	}

	@Override
	protected LevelView instantiateLevelView() {
		return new LevelView(getRoot(), getPlayer().getHealth());
	}

	private boolean isKillTargetReached() {
		return getPlayer().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}
}
