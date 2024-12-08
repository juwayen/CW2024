package com.example.demo.level;

import com.example.demo.Controller;
import com.example.demo.entity.plane.EnemyPlane;
import com.example.demo.screen.LevelFourEndScreen;
import com.example.demo.util.Vector;

public class LevelFour extends Level {
	private static final int TOTAL_ENEMIES = 4;
	private static final int KILLS_TO_ADVANCE = 20;
	private static final int ENEMY_INITIAL_Y_POS = -64;
	private static final int ENEMY_MAX_X_POSITION = 928;

	private final Controller controller;

	private int enemyCount;
	private int enemyPlanesDestroyed;
	private int totalEnemiesSpawned;

	public LevelFour(Controller controller) {
		super(controller, new LevelFourEndScreen());

		this.controller = controller;

		this.enemyCount = 0;
		this.enemyPlanesDestroyed = 0;
		this.totalEnemiesSpawned = 0;
	}

	@Override
	public void startLevel() {
		super.startLevel();

		enemyCount = 0;
		enemyPlanesDestroyed = 0;
		totalEnemiesSpawned = 0;

		getPlayer().getDestroyedSignal().connect(this::loseLevel);
	}

	@Override
	protected void spawnEnemyUnits() {
		if (totalEnemiesSpawned >= KILLS_TO_ADVANCE)
			return;

		for (int i = 0; i < TOTAL_ENEMIES - enemyCount; i++) {
			double spawnPosX = Math.random()  * ENEMY_MAX_X_POSITION;

			Vector spawnPos = new Vector(spawnPosX, ENEMY_INITIAL_Y_POS);

			EnemyPlane newEnemy = new EnemyPlane(controller, spawnPos);
			newEnemy.addToScene();

			enemyCount++;
			totalEnemiesSpawned++;

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
