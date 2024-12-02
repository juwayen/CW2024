package com.example.demo.level;

import com.example.demo.controller.GameController;
import com.example.demo.entity.enemy.EnemyPlane;

public class LevelOne extends LevelParent {
	private static final int TOTAL_ENEMIES = 2;
	private static final int KILLS_TO_ADVANCE = 20;
	private static final int ENEMY_INITIAL_Y_POS = -64;
	private static final int ENEMY_MAX_X_POSITION = 942;

	private final GameController gameController;

	public LevelOne(GameController gameController) {
		super(gameController);

		this.gameController = gameController;

		initialize();
	}

	private void initialize() {
		connectSignals();
	}

	private void connectSignals() {
		getPlayer().getEnemyPlaneDestroyedSignal().connect(this::onEnemyPlaneDestroyed);
	}

	private void onEnemyPlaneDestroyed() {
		if (isKillTargetReached())
			winLevel();
	}

	private boolean isKillTargetReached() {
		return getPlayer().getNumberOfKills() >= KILLS_TO_ADVANCE;
	}

	@Override
	protected void spawnEnemyUnits() {
		int currentNumberOfEnemies = getEnemyCount();

		for (int i = 0; i < TOTAL_ENEMIES - currentNumberOfEnemies; i++) {
			double newEnemyInitialXPosition = Math.random()  * ENEMY_MAX_X_POSITION;
			EnemyPlane newEnemy = new EnemyPlane(gameController, newEnemyInitialXPosition, ENEMY_INITIAL_Y_POS);
			newEnemy.addToScene();

			setEnemyCount(getEnemyCount() + 1);

			newEnemy.getPlaneDestroyedSignal().connect(this::decrementEnemyCount);
			newEnemy.getDefensesBreachedSignal().connect(this::onDefensesBreached);
		}
	}

	private void decrementEnemyCount() {
		setEnemyCount(getEnemyCount() - 1);
	}

	private void onDefensesBreached() {
		loseLevel();
	}
}
