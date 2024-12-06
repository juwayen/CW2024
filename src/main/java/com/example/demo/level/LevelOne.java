package com.example.demo.level;

import com.example.demo.GameController;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.util.Vector;

public class LevelOne extends Level {
	private static final int TOTAL_ENEMIES = 3;
	private static final int KILLS_TO_ADVANCE = 30;
	private static final int ENEMY_INITIAL_Y_POS = -64;
	private static final int ENEMY_MAX_X_POSITION = 928;

	private final GameController gameController;

	private int enemyCount;
	private int enemyPlanesDestroyed;

	public LevelOne(GameController gameController) {
		super(gameController);

		this.gameController = gameController;

		this.enemyCount = 0;
		this.enemyPlanesDestroyed = 0;
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
			double spawnPosX = Math.random()  * ENEMY_MAX_X_POSITION;

			Vector spawnPos = new Vector(spawnPosX, ENEMY_INITIAL_Y_POS);

			EnemyPlane newEnemy = new EnemyPlane(gameController, spawnPos);
			newEnemy.addToScene();

			enemyCount++;

			newEnemy.getRemovedSignal().connect(this::decrementEnemyCount);
			newEnemy.getDestroyedSignal().connect(this::onEnemyPlaneDestroyed);
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
}
