package com.example.demo.level;

import com.example.demo.controller.GameController;
import com.example.demo.entity.enemy.EnemyPlane;

public class LevelOne extends LevelParent {
	private static final int TOTAL_ENEMIES = 2;
	private static final int KILLS_TO_ADVANCE = 20;
	private static final int ENEMY_INITIAL_Y_POS = -64;
	private static final int ENEMY_MAX_X_POSITION = 960;

	private final GameController gameController;

	private int enemyCount = 0;
	private int enemyPlanesDestroyed = 0;

	public LevelOne(GameController gameController) {
		super(gameController);

		this.gameController = gameController;
	}

	@Override
	public void startLevel() {
		super.startLevel();
		enemyCount = 0;
		enemyPlanesDestroyed = 0;
		getPlayer().getDestroyedSignal().connect(this::loseLevel);
	}

	@Override
	protected void spawnEnemyUnits() {
		for (int i = 0; i < TOTAL_ENEMIES - enemyCount; i++) {
			double newEnemyInitialXPosition = Math.random()  * ENEMY_MAX_X_POSITION;
			EnemyPlane newEnemy = new EnemyPlane(gameController, newEnemyInitialXPosition, ENEMY_INITIAL_Y_POS);
			newEnemy.addToScene();

			enemyCount++;

			newEnemy.getRemovedSignal().connect(this::decrementEnemyCount);
			newEnemy.getDestroyedSignal().connect(this::onEnemyPlaneDestroyed);
			newEnemy.getDefensesBreachedSignal().connect(this::onDefensesBreached);
		}
	}

	private void decrementEnemyCount() {
		enemyCount--;
	}

	private void onEnemyPlaneDestroyed() {
		enemyPlanesDestroyed++;

		if (enemyPlanesDestroyed >= KILLS_TO_ADVANCE)
			winLevel();
	}

	private void onDefensesBreached() {
		loseLevel();
	}
}
